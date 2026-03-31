create database PRJ_CYBER_JAVA_04;

use PRJ_CYBER_JAVA_04;
-- drop database PRJ_CYBER_JAVA_04;

-- =============================================
create table users(
    user_id varchar(10) primary key,
    username varchar(50) not null unique,
    password varchar(100) not null,
    full_name varchar(100) not null,
    phone varchar(15) not null,
    role varchar(10) not null
        check (role in ('admin','staff','customer')),
    balance decimal(10,2) default 0,
    created_at datetime default current_timestamp
);

create table categories(
	cate_id int auto_increment primary key,
    cate_name varchar(20) not null
);

create table pcs(
    pc_id INT AUTO_INCREMENT PRIMARY KEY,
    pc_name VARCHAR(50) NOT NULL UNIQUE,
    cate_id int,
    status VARCHAR(20) DEFAULT 'available'
        CHECK (status IN ('available','using','maintenance')),
    priceHoue DECIMAL(10,2) NOT NULL,
    foreign key(cate_id) references categories(cate_id)
);

create table foods(
    food_id int auto_increment primary key,
    food_name varchar(100) not null,
    price decimal(10,2) not null,
    stock int default 0,
    type varchar(50) check (type in ('food' , 'drink'))
);

create table bookings(
    booking_id int auto_increment primary key,
    user_id varchar(10) not null,
    pc_id int not null,
    start_time datetime not null,
    end_time datetime,
    total_cost decimal(10,2),
    foreign key (user_id) references users(user_id),
    foreign key (pc_id) references pcs(pc_id)
);

create table orders (
    order_id int auto_increment primary key,
    user_id varchar(10) not null,
    food_id int not null,
    quantity int not null,
    total_price decimal(10,2) not null,
    order_time datetime default current_timestamp,
    status varchar(20) default 'pending'
        check (status in ('pending','preparing','completed','cancelled')),
    
    foreign key (user_id) references users(user_id),
    foreign key (food_id) references foods(food_id)
);

-- ===================== users =========================
insert into users(user_id, username, password, full_name, phone, role, balance)
values
('U001','admin01','123456','Nguyen Van Admin','0900000001','admin',0),
('U002','staff01','123456','Tran Van Staff','0900000002','staff',0),
('U003','customer01','123456','Le Van Customer','0900000003','customer',100000),
('U004','customer02','123456','Nguyen Van Customer','0900000002','customer',100000);
delimiter $$

create procedure sp_login(
    in p_username varchar(50),
    in p_password varchar(255)
)
begin
    select user_id, username, password, full_name, phone, role, balance, created_at
    from users
    where username = p_username
    and password = p_password;
end $$

create procedure sp_register(
    in p_user_id varchar(10),
    in p_username varchar(50),
    in p_password varchar(255),
    in p_full_name varchar(100),
    in p_phone varchar(15),
    in p_role varchar(10)
)
begin
    insert into users(user_id,username,password,full_name,phone,role)
    values(p_user_id,p_username,p_password,p_full_name,p_phone,p_role);
end $$


create procedure getallusers()
begin
    select user_id, username, password, full_name, phone, role, balance, created_at from users;
end $$

create procedure addUser(
    in p_user_id varchar(10),
    in p_username varchar(50),
    in p_password varchar(255),
    in p_full_name varchar(100),
    in p_phone varchar(15),
    in p_role varchar(10),
    in p_balance decimal(10,2)
)
begin
    insert into users(user_id,username,password,full_name,phone,role,balance)
    values(p_user_id,p_username,p_password,p_full_name,p_phone,p_role,p_balance);
end $$

create procedure updateuser(
    p_username varchar(50),
    p_password varchar(100),
    p_full_name varchar(100),
    p_phone varchar(15),
    p_role varchar(10),
    p_balance decimal(10,2)
)
begin
    update users
    set password = p_password,
        full_name = p_full_name,
        phone = p_phone,
        role = p_role,
        balance = p_balance
    where username = p_username;
end $$

create procedure deleteuser(
    p_username varchar(50)
)
begin
    delete from users
    where username = p_username;
end $$

-- lay id theo username
create procedure getIdByNameUser(
	in p_username varchar(50),
    out p_user_id varchar(10)
)
begin
	select user_id into  p_user_id from users where username  = p_username limit 1;
end $$
delimiter ;

-- ====================== category ==============
-- Insert dữ liệu mẫu vào categories
insert into categories(cate_name)
values('normal'),('vip'),('double');

-- lay id theo ten 
delimiter ..
create procedure getNameByIdCate(
	in p_cate_name varchar(20),
    out p_cate_id int
    
)
begin
	select cate_id into p_cate_id from categories where cate_name  = p_cate_name limit 1;
end ..

-- lay toan bo du lieu	
create procedure getAll()
begin
	select *from  categories;
end ..
delimiter ;

-- ================== PCs ==========================
INSERT INTO pcs(pc_name, cate_id, status, priceHoue) VALUES
('PC01', 1, 'available', 50000),
('PC02', 2, 'available', 100000),
('PC03', 3, 'maintenance', 50000);


delimiter ..

-- Lấy tất cả PC
create procedure getAllPc()
begin
    select * from pcs;
end ..

-- Thêm PC
create procedure insertPcs(
    p_pc_name varchar(50),
    p_cate_id int,
    p_status varchar(20),
    p_priceHoue decimal(10,2)
)
begin
    insert into pcs(pc_name, cate_id, status, priceHoue) 
    values (p_pc_name, p_cate_id ,  p_status, p_priceHoue);
end ..

-- xox PC 
create procedure deletePcs(
    p_pc_name varchar(50)
)
begin
    delete from pcs where pc_name = p_pc_name;
end ..

-- sua
create procedure updatePcs(
    p_pc_name varchar(50),
    p_cate_id int,
    p_status varchar(20),
    p_priceHoue decimal(10,2)
)
begin
    update pcs 
    set cate_id = p_cate_id, status = p_status, priceHoue = p_priceHoue 
    where pc_name = p_pc_name;
end ..
 
 
 -- sua trang thai 
 create procedure updatepcStatusToBooking(in p_pc_id int)
begin
    update pcs
    set status = 'using'
    where pc_id = p_pc_id;
end..

create procedure closeStatusToBooking(in p_pc_id int)
begin
    update pcs
    set status = 'available'
    where pc_id = p_pc_id;
end..

create procedure updatebookingendtimeandcost(
    in p_pc_id int,
    in p_end_time datetime,
    in p_total_cost decimal(10,2)
)
begin
    update bookings
    set end_time = p_end_time,
        total_cost = p_total_cost
    where pc_id = p_pc_id;
end ..
DELIMITER ;
-- ================= Foods ========================
insert into foods(food_name, price, stock, type) values
('Hamburger', 50000, 10, 'food'),
('Coca Cola', 15000, 20, 'drink'),
('Pizza', 75000, 5, 'food');
delimiter ..
-- them 
create procedure insertfood(
    p_food_name varchar(100),
    p_price decimal(10,2),
    p_stock int,
    p_type varchar(50)
)
begin
    insert into foods(food_name, price, stock, type)
    values (p_food_name, p_price, p_stock, p_type);
end ..
-- lay
create procedure getallfood()
begin
    select * from foods;
end ..
-- sua 
create procedure updatefood(
    p_food_name varchar(100),
    p_price decimal(10,2),
    p_stock int,
    p_type varchar(50)
)
begin
    update foods
    set price = p_price,
        stock = p_stock,
        type = p_type
    where food_name = p_food_name;
end ..

-- xoa 
create procedure deletefood(
    p_food_name varchar(100)
)
begin
    delete from foods
    where food_name = p_food_name;
end ..

-- trừ đi số lượng 
create procedure updatefoodquantity(
    in p_food_name varchar(50),
    in p_quantity int,
    out p_success boolean
)
begin
    if exists (
        select 1 
        from foods 
        where food_name = p_food_name 
        and stock >= p_quantity
    ) then
    
        update foods
        set stock = stock - p_quantity
        where food_name = p_food_name;
        
        set p_success = true;
        
    else
        set p_success = false;
        
    end if;
end ..
delimiter ;

-- ================== bookings ===================	

insert into bookings(user_id, pc_id, start_time, end_time, total_cost)
values
('U004', 1, '2026-03-30 08:00:00', '2026-03-30 10:00:00', 1000.00),
('U004', 2, '2026-03-30 09:30:00', '2026-03-30 11:00:00', 750.00),
('U003', 3, '2026-03-30 10:00:00', '2026-03-30 12:30:00', 1250.00),



delimiter ..
-- lay du lieu
create procedure getAllBooking()
begin
	select *from bookings;
end ..

-- dat booking
create procedure addBooking(
	p_user_id varchar(10),
	p_pc_id int,
	p_start_time datetime,
	p_end_time datetime,
	p_total_cost decimal(10,2)
)
begin
	insert into bookings(user_id,pc_id,start_time,end_time,total_cost) values (p_user_id,p_pc_id,p_start_time,p_end_time,p_total_cost);
end ..

delimiter ;

-- ================= oders ====================
-- Insert dữ liệu mẫu vào orders
insert into orders(user_id, food_id, quantity, total_price, status)
values
('U002', 1, 2, 50000, 'pending'),
('U002', 2, 1, 30000, 'preparing'),
('U002', 3, 3, 90000, 'completed');

--  
delimiter //
-- them đơn hàng
create procedure addorder(
    in p_user_id varchar(10),
    in p_food_id int,
    in p_quantity int,
    in p_total_price decimal(10,2),
    in p_status varchar(20)
)
begin
    insert into orders(user_id, food_id, quantity, total_price, status)
    values (p_user_id, p_food_id, p_quantity, p_total_price, p_status);
end //

-- lấy tất cả 
create procedure getallorders()
begin
    select order_id, user_id, food_id, quantity, total_price, order_time, status
    from orders;
end //

create procedure updateorderstatus(
    in p_user_id varchar(50),
    in p_status varchar(20)
)
begin
    -- Cập nhật trạng thái đơn hàng
    update orders
    set status = p_status
    where user_id = p_user_id
      and p_status in ('pending','preparing','completed','cancelled'); 
end //
delimiter ;




