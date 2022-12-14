USE [master]
GO
/****** Object:  Database [Midori_mart]    Script Date: 10/9/2022 10:54:08 AM ******/
CREATE DATABASE [Midori_mart]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Midori_mart', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER2019\MSSQL\DATA\Midori_mart.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Midori_mart_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER2019\MSSQL\DATA\Midori_mart_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Midori_mart] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Midori_mart].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Midori_mart] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Midori_mart] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Midori_mart] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Midori_mart] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Midori_mart] SET ARITHABORT OFF 
GO
ALTER DATABASE [Midori_mart] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Midori_mart] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Midori_mart] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Midori_mart] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Midori_mart] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Midori_mart] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Midori_mart] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Midori_mart] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Midori_mart] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Midori_mart] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Midori_mart] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Midori_mart] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Midori_mart] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Midori_mart] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Midori_mart] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Midori_mart] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Midori_mart] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Midori_mart] SET RECOVERY FULL 
GO
ALTER DATABASE [Midori_mart] SET  MULTI_USER 
GO
ALTER DATABASE [Midori_mart] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Midori_mart] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Midori_mart] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Midori_mart] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Midori_mart] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Midori_mart] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Midori_mart', N'ON'
GO
ALTER DATABASE [Midori_mart] SET QUERY_STORE = OFF
GO
USE [Midori_mart]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Country]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Country](
	[code] [int] NOT NULL,
	[name] [nvarchar](255) NULL,
	[continent_name] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feedback](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[firstname] [nvarchar](255) NULL,
	[lastname] [nvarchar](255) NULL,
	[email] [nvarchar](255) NULL,
	[phone_number] [nvarchar](255) NULL,
	[subject_name] [nvarchar](255) NULL,
	[note] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Gallery]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Gallery](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[product_id] [int] NULL,
	[thumbnail] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Merchant_periods]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Merchant_periods](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[merchant_id] [int] NULL,
	[country_code] [int] NULL,
	[start_date] [datetime] NULL,
	[end_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Merchant_Product]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Merchant_Product](
	[product_id] [int] NOT NULL,
	[country_code] [int] NOT NULL,
	[merchant_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[product_id] ASC,
	[country_code] ASC,
	[merchant_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Merchants]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Merchants](
	[id] [int] NOT NULL,
	[country_code] [int] NOT NULL,
	[merchant_name] [nvarchar](255) NULL,
	[created_at] [nvarchar](255) NULL,
	[admin_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC,
	[country_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[full_name] [nvarchar](255) NULL,
	[email] [nvarchar](255) NULL,
	[phone_number] [nvarchar](255) NULL,
	[address] [nvarchar](255) NULL,
	[note] [nvarchar](255) NULL,
	[order_date] [datetime] NULL,
	[status] [int] NULL,
	[total_money] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order_Detail]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order_Detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NULL,
	[order_id] [int] NULL,
	[product_id] [int] NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[total_money] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Permission]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Permission](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[per_name] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Permission_detail]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Permission_detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[per_id] [int] NULL,
	[action_name] [nvarchar](255) NULL,
	[action_code] [nvarchar](255) NULL,
	[check_action] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[title] [nvarchar](255) NULL,
	[price] [float] NULL,
	[discount] [int] NULL,
	[thumbnail] [nvarchar](255) NULL,
	[description] [nvarchar](255) NULL,
	[status] [varchar](50) NOT NULL,
	[created_at] [datetime] NULL,
	[updated_at] [datetime] NULL,
	[deleted] [int] NULL,
 CONSTRAINT [PK__Product__3213E83FDF522CD7] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[email] [nvarchar](255) NULL,
	[password] [nvarchar](255) NULL,
	[phone_number] [nvarchar](255) NULL,
	[full_name] [nvarchar](255) NULL,
	[address] [nvarchar](255) NULL,
	[created_at] [datetime] NULL,
	[updated_at] [datetime] NULL,
	[deleted] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User_Per]    Script Date: 10/9/2022 10:54:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_Per](
	[user_id] [int] NOT NULL,
	[per_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC,
	[per_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Category] ON 

INSERT [dbo].[Category] ([id], [name]) VALUES (1, N'Meat')
INSERT [dbo].[Category] ([id], [name]) VALUES (2, N'Thịt')
INSERT [dbo].[Category] ([id], [name]) VALUES (3, N'Thủy Hải Sản')
INSERT [dbo].[Category] ([id], [name]) VALUES (4, N'Đồ Khô')
SET IDENTITY_INSERT [dbo].[Category] OFF
GO
SET IDENTITY_INSERT [dbo].[Product] ON 

INSERT [dbo].[Product] ([id], [category_id], [title], [price], [discount], [thumbnail], [description], [status], [created_at], [updated_at], [deleted]) VALUES (1, 1, N'Ba chỉ', 6.99, 2, NULL, NULL, N'running_low', CAST(N'2022-10-03T00:12:49.353' AS DateTime), CAST(N'2022-10-03T00:12:49.353' AS DateTime), 0)
INSERT [dbo].[Product] ([id], [category_id], [title], [price], [discount], [thumbnail], [description], [status], [created_at], [updated_at], [deleted]) VALUES (2, 3, N'Hạt Chia', 2.99, 0, NULL, N'This is Dried product', N'in_stock', CAST(N'2022-10-09T00:19:23.917' AS DateTime), CAST(N'2022-10-09T00:19:23.917' AS DateTime), 0)
INSERT [dbo].[Product] ([id], [category_id], [title], [price], [discount], [thumbnail], [description], [status], [created_at], [updated_at], [deleted]) VALUES (3, 4, N'Hạt Óc Chó', 2.99, 0, N'https://vcdn1-suckhoe.vnecdn.net/2019/12/07/3-1p60g42555v7-1575707575-1794-1575707805.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=wyEpSdJiINvuTN4TihP3fA', N'This is Dried product', N'in_stock', CAST(N'2022-10-09T00:21:03.633' AS DateTime), CAST(N'2022-10-09T00:21:03.633' AS DateTime), 1)
INSERT [dbo].[Product] ([id], [category_id], [title], [price], [discount], [thumbnail], [description], [status], [created_at], [updated_at], [deleted]) VALUES (4, 2, N'Thịt Thăn', 6.66, 0, N'https://vcdn1-suckhoe.vnecdn.net/2019/12/07/3-1p60g42555v7-1575707575-1794-1575707805.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=wyEpSdJiINvuTN4TihP3fA', N'This is Meat product', N'in_stock', CAST(N'2022-10-09T00:21:37.643' AS DateTime), CAST(N'2022-10-09T00:21:37.643' AS DateTime), 0)
INSERT [dbo].[Product] ([id], [category_id], [title], [price], [discount], [thumbnail], [description], [status], [created_at], [updated_at], [deleted]) VALUES (5, 2, N'Thịt Bò', 6.66, 0, N'https://vcdn1-suckhoe.vnecdn.net/2019/12/07/3-1p60g42555v7-1575707575-1794-1575707805.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=wyEpSdJiINvuTN4TihP3fA', N'This is Meat product', N'in_stock', CAST(N'2022-10-09T00:21:42.167' AS DateTime), CAST(N'2022-10-09T00:21:42.167' AS DateTime), 0)
INSERT [dbo].[Product] ([id], [category_id], [title], [price], [discount], [thumbnail], [description], [status], [created_at], [updated_at], [deleted]) VALUES (6, 3, N'Cá Bò', 15.66, 0, N'https://vcdn1-suckhoe.vnecdn.net/2019/12/07/3-1p60g42555v7-1575707575-1794-1575707805.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=wyEpSdJiINvuTN4TihP3fA', N'This is Fish product', N'in_stock', CAST(N'2022-10-09T00:22:01.047' AS DateTime), CAST(N'2022-10-09T00:22:01.047' AS DateTime), 0)
INSERT [dbo].[Product] ([id], [category_id], [title], [price], [discount], [thumbnail], [description], [status], [created_at], [updated_at], [deleted]) VALUES (7, 3, N'Cá Heo', 15.66, 0, N'https://vcdn1-suckhoe.vnecdn.net/2019/12/07/3-1p60g42555v7-1575707575-1794-1575707805.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=wyEpSdJiINvuTN4TihP3fA', N'This is Fish product', N'in_stock', CAST(N'2022-10-09T00:22:05.783' AS DateTime), CAST(N'2022-10-09T00:22:05.783' AS DateTime), 0)
SET IDENTITY_INSERT [dbo].[Product] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_qka6vxqdy1dprtqnx9trdd47c]    Script Date: 10/9/2022 10:54:09 AM ******/
ALTER TABLE [dbo].[Product] ADD  CONSTRAINT [UK_qka6vxqdy1dprtqnx9trdd47c] UNIQUE NONCLUSTERED 
(
	[title] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Product] ADD  CONSTRAINT [DF__Product__created__2F10007B]  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Product] ADD  CONSTRAINT [DF_Product_updated_at]  DEFAULT (getdate()) FOR [updated_at]
GO
ALTER TABLE [dbo].[Gallery]  WITH CHECK ADD  CONSTRAINT [FK__Galery__product___44FF419A] FOREIGN KEY([product_id])
REFERENCES [dbo].[Product] ([id])
GO
ALTER TABLE [dbo].[Gallery] CHECK CONSTRAINT [FK__Galery__product___44FF419A]
GO
ALTER TABLE [dbo].[Merchant_periods]  WITH CHECK ADD FOREIGN KEY([merchant_id], [country_code])
REFERENCES [dbo].[Merchants] ([id], [country_code])
GO
ALTER TABLE [dbo].[Merchant_Product]  WITH CHECK ADD  CONSTRAINT [FK__merchant___produ__4AB81AF0] FOREIGN KEY([product_id])
REFERENCES [dbo].[Product] ([id])
GO
ALTER TABLE [dbo].[Merchant_Product] CHECK CONSTRAINT [FK__merchant___produ__4AB81AF0]
GO
ALTER TABLE [dbo].[Merchant_Product]  WITH CHECK ADD FOREIGN KEY([merchant_id], [country_code])
REFERENCES [dbo].[Merchants] ([id], [country_code])
GO
ALTER TABLE [dbo].[Merchants]  WITH CHECK ADD FOREIGN KEY([admin_id])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Merchants]  WITH CHECK ADD FOREIGN KEY([country_code])
REFERENCES [dbo].[Country] ([code])
GO
ALTER TABLE [dbo].[Order_Detail]  WITH CHECK ADD  CONSTRAINT [FK__Order_Det__produ__4316F928] FOREIGN KEY([product_id])
REFERENCES [dbo].[Product] ([id])
GO
ALTER TABLE [dbo].[Order_Detail] CHECK CONSTRAINT [FK__Order_Det__produ__4316F928]
GO
ALTER TABLE [dbo].[Order_Detail]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Order_Detail]  WITH CHECK ADD FOREIGN KEY([id])
REFERENCES [dbo].[Order] ([id])
GO
ALTER TABLE [dbo].[Permission_detail]  WITH CHECK ADD FOREIGN KEY([per_id])
REFERENCES [dbo].[Permission] ([id])
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK__Product__categor__4222D4EF] FOREIGN KEY([category_id])
REFERENCES [dbo].[Category] ([id])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK__Product__categor__4222D4EF]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK1mtsbur82frn64de7balymq9s] FOREIGN KEY([category_id])
REFERENCES [dbo].[Category] ([id])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK1mtsbur82frn64de7balymq9s]
GO
ALTER TABLE [dbo].[User_Per]  WITH CHECK ADD FOREIGN KEY([per_id])
REFERENCES [dbo].[Permission] ([id])
GO
ALTER TABLE [dbo].[User_Per]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [CK__Product__status__2E1BDC42] CHECK  (([status]='running_low' OR [status]='in_stock' OR [status]='out_of_stock'))
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [CK__Product__status__2E1BDC42]
GO
USE [master]
GO
ALTER DATABASE [Midori_mart] SET  READ_WRITE 
GO
