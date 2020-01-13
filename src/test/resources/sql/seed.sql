insert into Oil_Type (oil_Type, description) values ('Premium', 'Premium type');
insert into Oil_Type (oil_Type, description) values ('Standard', 'Standard type');

insert into Oil (oil_Id, oil_Type, fixed_Revenue, oil_Barrel_Value) values ('AAC', 'Standard', 1, 42);
insert into Oil (oil_Id, oil_Type, fixed_Revenue, oil_Barrel_Value) values ('REW', 'Standard', 7, 47);
insert into Oil (oil_Id, oil_Type, fixed_Revenue, oil_Barrel_Value) values ('BWO', 'Standard', 17, 61);
insert into Oil (oil_Id, oil_Type, fixed_Revenue, variable_Revenue, oil_Barrel_Value) values ('TIM', 'Premium', 5, 7, 111);
insert into Oil (oil_Id, oil_Type, fixed_Revenue, oil_Barrel_Value) values ('QFC', 'Standard', 22, 123);

insert into Oil_Transaction (transaction_Id, volume, price, operation, oil_Id, transaction_Date_Time) values (99, 10.55, 50.25, 'Buy', 'REW', '2020-01-12T22:37:06.691');
insert into Oil_Transaction (transaction_Id, volume, price, operation, oil_Id, transaction_Date_Time) values (90, 11.02, 48.25, 'Sell', 'BWO', '2020-01-12T22:37:06.691');