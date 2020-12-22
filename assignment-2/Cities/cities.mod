#SETS AND PARAMETERS    
set cities;
param x{cities} integer;
param y{cities} integer;

#VARIABLES
var pair{cities,cities} binary;

#PROBLEM
#maximize sum of euclidean distances between all pairs
maximize sum_euclidean_distances: 
    sum{i in cities, j in cities} pair[i,j]*sqrt((x[i]-x[j])*(x[i]-x[j])+(y[i]-y[j])*(y[i]-y[j]));

#st each city only having one partner
subject to row_uniqueness{i in cities}: 
	sum{j in cities} (pair[i,j]) = 1;
    
#subject to column_uniqueness{i in cities}: 
	sum{j in cities} (pair[j,i]) = 1;