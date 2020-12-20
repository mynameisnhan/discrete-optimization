param capacity integer;
set items;
param value {items} integer;
param weight {items} integer;

var x{items} integer >=0;

maximize used: 
    sum{i in items} x[i]*value[i];

subject to knapsack: 
    sum{i in items} (weight[i]*x[i]) <= capacity;