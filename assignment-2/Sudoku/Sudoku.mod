param size;
set fixedFields within {(1..size) cross (1..size)};
param fixedValues{fixedFields};

var x {(i,j,k) in {(1..size) cross (1..size) cross (1..size)}} binary;

maximize used: x[1,1,1];

subject to Columns {j in (1..size), k in (1..size)}:
	sum{i in (1..size)} x[i,j,k] = 1;

subject to Rows {i in 1..size, k in 1..size}:
	sum{j in 1..size} x[i,j,k] =1;

subject to Submatrices {k in 1..size, p in 1..sqrt(size), q in 1..sqrt(size)}:
	sum {j in ((sqrt(size)*p - (sqrt(size)-1))..(sqrt(size)*p))} sum {i in ((sqrt(size)*q - (sqrt(size)-1))..(sqrt(size)*q))} x[i,j,k] = 1;	

subject to Filled {i in 1..size, j in 1..size}:
	sum {k in 1..size} x[i,j,k] = 1;	

subject to KnownEntries {(i,j) in fixedFields}:
	x[i,j,fixedValues[i,j]] = 1;	