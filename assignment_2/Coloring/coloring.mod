#SETS
#set of countries (nodes)
set COUNTRIES;

#set of borders (edges)
set BORDERS within {COUNTRIES, COUNTRIES};

#set of colors
set COLORS;

#VARIABLES
#x[i,j] = 1 if country i takes color j
var x{COUNTRIES,COLORS} binary;

#y[j] = 1 if color j is used
var y{COLORS} binary;

#PROBLEM
#minimize number of used colors
minimize used:
	sum{j in COLORS} y[j];

#a color per country
subject to assignment{i in COUNTRIES}:
	sum{j in COLORS} x[i,j] = 1;

#different colors for bordering countries
subject to diversity{(i1, i2) in BORDERS, j in COLORS}:
	x[i1,j]+x[i2,j] <= 1;

#x and y link
subject to link{i in COUNTRIES, j in COLORS}:
	x[i,j] <= y[j];