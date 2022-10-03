

CREATE TABLE [merchants] (
  [id] int,
  [country_code] int,
  [merchant_name] nvarchar(255),
  [created_at] nvarchar(255),
  [admin_id] int,
  PRIMARY KEY ([id], [country_code])
)
GO

CREATE TABLE [countries] (
  [code] int PRIMARY KEY,
  [name] nvarchar(255),
  [continent_name] nvarchar(255)
)
GO

CREATE TABLE [merchant_periods] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [merchant_id] int,
  [country_code] int,
  [start_date] datetime,
  [end_date] datetime
)
GO

CREATE TABLE [User] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [email] nvarchar(255),
  [password] nvarchar(255),
  [phone_number] nvarchar(255),
  [full_name] nvarchar(255),
  [address] nvarchar(255),
  [created_at] datetime,
  [updated_at] datetime,
  [deleted] int
)
GO

CREATE TABLE [Category] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [name] nvarchar(255)
)
GO

CREATE TABLE [Product] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [category_id] int,
  [title] nvarchar(255),
  [price] float,
  [discount] int,
  [thumbnail] nvarchar(255),
  [description] nvarchar(255),
  [status] nvarchar(255) NOT NULL CHECK ([status] IN ('out_of_stock', 'in_stock', 'running_low')),
  [created_at] datetime DEFAULT 'now()',
  [updated_at] datetime,
  [deleted] int
)
GO

CREATE TABLE [merchant_product] (
  [product_id] int,
  [country_code] int,
  [merchant_id] int,
  PRIMARY KEY ([product_id], [country_code], [merchant_id])
)
GO

CREATE TABLE [Galery] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [product_id] int,
  [thumbnail] nvarchar(255)
)
GO

CREATE TABLE [Feedback] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [firstname] nvarchar(255),
  [lastname] nvarchar(255),
  [email] nvarchar(255),
  [phone_number] nvarchar(255),
  [subject_name] nvarchar(255),
  [note] nvarchar(255)
)
GO

CREATE TABLE [Order] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [full_name] nvarchar(255),
  [email] nvarchar(255),
  [phone_number] nvarchar(255),
  [address] nvarchar(255),
  [note] nvarchar(255),
  [order_date] datetime,
  [status] int,
  [total_money] float
)
GO

CREATE TABLE [Order_Detail] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [user_id] int,
  [order_id] int,
  [product_id] int,
  [price] float,
  [quantity] int,
  [total_money] float
)
GO

CREATE TABLE [Permission] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [per_name] nvarchar(255)
)
GO

CREATE TABLE [permission_detail] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [per_id] int,
  [action_name] nvarchar(255),
  [action_code] nvarchar(255),
  [check_action] bit
)
GO

CREATE TABLE [user_per] (
  [user_id] int,
  [per_id] int,
  PRIMARY KEY ([user_id], [per_id])
)
GO

ALTER TABLE [merchants] ADD FOREIGN KEY ([country_code]) REFERENCES [countries] ([code])
GO

ALTER TABLE [merchant_periods] ADD FOREIGN KEY ([merchant_id], [country_code]) REFERENCES [merchants] ([id], [country_code])
GO

ALTER TABLE [merchant_product] ADD FOREIGN KEY ([merchant_id], [country_code]) REFERENCES [merchants] ([id], [country_code])
GO

ALTER TABLE [Product] ADD FOREIGN KEY ([category_id]) REFERENCES [Category] ([id])
GO

ALTER TABLE [Order_Detail] ADD FOREIGN KEY ([product_id]) REFERENCES [Product] ([id])
GO

ALTER TABLE [Order_Detail] ADD FOREIGN KEY ([id]) REFERENCES [Order] ([id])
GO

ALTER TABLE [Galery] ADD FOREIGN KEY ([product_id]) REFERENCES [Product] ([id])
GO

ALTER TABLE [Order_Detail] ADD FOREIGN KEY ([user_id]) REFERENCES [User] ([id])
GO

ALTER TABLE [user_per] ADD FOREIGN KEY ([per_id]) REFERENCES [Permission] ([id])
GO

ALTER TABLE [permission_detail] ADD FOREIGN KEY ([per_id]) REFERENCES [Permission] ([id])
GO

ALTER TABLE [user_per] ADD FOREIGN KEY ([user_id]) REFERENCES [User] ([id])
GO

ALTER TABLE [merchants] ADD FOREIGN KEY ([admin_id]) REFERENCES [User] ([id])
GO

ALTER TABLE [merchant_product] ADD FOREIGN KEY ([product_id]) REFERENCES [Product] ([id])
GO
