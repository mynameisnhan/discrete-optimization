#SETS AND PARAMETERS
param capacity integer;
set items;
param value{items} integer;
param weight{items} integer;

#VARIABLES
var selection{items} binary;

#PROBLEM
#maximize value 
maximize knapsack_value: 
    sum{i in items} selection[i]*value[i];

#st weights <= capacity
subject to knapsack_capacity: 
    sum{i in items} weight[i]*selection[i] <= capacity;