USE [master]
GO
/****** Object:  Database [Midori_mart]    Script Date: 10/25/2022 10:10:07 PM ******/
CREATE DATABASE [Midori_mart]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Midori_mart', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER2019\MSSQL\DATA\Midori_mart2_new.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Midori_mart_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER2019\MSSQL\DATA\Midori_mart2_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
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
/****** Object:  Table [dbo].[Category]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
 CONSTRAINT [PK__Category__3213E83F7D6C209A] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Comment]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Comment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[content] [nvarchar](255) NULL,
	[created_at] [datetime2](7) NULL,
	[updated_at] [datetime2](7) NULL,
	[product_id] [int] NULL,
	[user_id] [int] NULL,
 CONSTRAINT [PK__Comment__3213E83FF624E309] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Country]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Country](
	[code] [varchar](255) NOT NULL,
	[name] [nvarchar](255) NULL,
 CONSTRAINT [PK__Country__357D4CF8F25960A2] PRIMARY KEY CLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 10/25/2022 10:10:07 PM ******/
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
/****** Object:  Table [dbo].[Gallery]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Gallery](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[thumbnail] [varchar](255) NULL,
	[product_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Merchant]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Merchant](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[created_at] [datetime2](7) NULL,
	[merchant_name] [nvarchar](255) NULL,
	[admin_id] [int] NULL,
	[country_code] [varchar](255) NULL,
 CONSTRAINT [PK__Merchant__3213E83F610EF52D] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Merchant_Period]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Merchant_Period](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[merchant_id] [int] NULL,
	[country_code] [varchar](3) NULL,
	[start_date] [datetime] NULL,
	[end_date] [datetime] NULL,
 CONSTRAINT [PK__Merchant__3213E83F41BE8A23] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 10/25/2022 10:10:07 PM ******/
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
/****** Object:  Table [dbo].[Order_Detail]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order_Detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
	[total_money] [float] NOT NULL,
	[order_id] [int] NULL,
	[product_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Permission]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Permission](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[action_name] [nvarchar](255) NULL,
	[action_code] [nvarchar](255) NULL,
	[path] [varchar](255) NULL,
	[method] [varchar](10) NULL,
	[check_action] [bit] NULL,
 CONSTRAINT [PK__permissi__3213E83F57DBEFE6] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[amount] [float] NOT NULL,
	[created_at] [datetime2](7) NULL,
	[deleted] [int] NOT NULL,
	[description] [varchar](255) NULL,
	[discount] [int] NOT NULL,
	[price] [float] NOT NULL,
	[sku] [varchar](255) NULL,
	[slug] [varchar](255) NULL,
	[status] [varchar](255) NULL,
	[title] [varchar](255) NULL,
	[updated_at] [datetime2](7) NULL,
	[category_id] [int] NULL,
	[merchant_id] [int] NULL,
	[product_unit_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK_88yb4l9100epddqsrdvxerhq9] UNIQUE NONCLUSTERED 
(
	[slug] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK_q1mafxn973ldq80m1irp3mpvq] UNIQUE NONCLUSTERED 
(
	[sku] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK_qka6vxqdy1dprtqnx9trdd47c] UNIQUE NONCLUSTERED 
(
	[title] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product_Unit]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product_Unit](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
 CONSTRAINT [PK__Product___3213E83FA1C8E4B8] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_name] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role_Permission]    Script Date: 10/25/2022 10:10:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role_Permission](
	[role_id] [int] NOT NULL,
	[permission_id] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 10/25/2022 10:10:07 PM ******/
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
	[role_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK_ob8kqyqqgmefl0aco34akdtpe] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[User] ADD  CONSTRAINT [DF_User_role_id]  DEFAULT ((2)) FOR [role_id]
GO
ALTER TABLE [dbo].[Comment]  WITH CHECK ADD  CONSTRAINT [FK_Comment_User] FOREIGN KEY([user_id])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Comment] CHECK CONSTRAINT [FK_Comment_User]
GO
ALTER TABLE [dbo].[Comment]  WITH CHECK ADD  CONSTRAINT [FKm1rmnfcvq5mk26li4lit88pc5] FOREIGN KEY([product_id])
REFERENCES [dbo].[Product] ([id])
GO
ALTER TABLE [dbo].[Comment] CHECK CONSTRAINT [FKm1rmnfcvq5mk26li4lit88pc5]
GO
ALTER TABLE [dbo].[Comment]  WITH CHECK ADD  CONSTRAINT [FKn6xssinlrtfnm61lwi0ywn71q] FOREIGN KEY([user_id])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Comment] CHECK CONSTRAINT [FKn6xssinlrtfnm61lwi0ywn71q]
GO
ALTER TABLE [dbo].[Gallery]  WITH CHECK ADD  CONSTRAINT [FKoo2h8inluisbx700nbtwf2scg] FOREIGN KEY([product_id])
REFERENCES [dbo].[Product] ([id])
GO
ALTER TABLE [dbo].[Gallery] CHECK CONSTRAINT [FKoo2h8inluisbx700nbtwf2scg]
GO
ALTER TABLE [dbo].[Merchant]  WITH CHECK ADD  CONSTRAINT [FKgcae8kmeijk2q7v3fpxq14bmt] FOREIGN KEY([country_code])
REFERENCES [dbo].[Country] ([code])
GO
ALTER TABLE [dbo].[Merchant] CHECK CONSTRAINT [FKgcae8kmeijk2q7v3fpxq14bmt]
GO
ALTER TABLE [dbo].[Merchant]  WITH CHECK ADD  CONSTRAINT [FKr0n86pesjh2nb9jjodmjijx5f] FOREIGN KEY([admin_id])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Merchant] CHECK CONSTRAINT [FKr0n86pesjh2nb9jjodmjijx5f]
GO
ALTER TABLE [dbo].[Merchant_Period]  WITH CHECK ADD  CONSTRAINT [FK_Merchant_Period_Merchant] FOREIGN KEY([merchant_id])
REFERENCES [dbo].[Merchant] ([id])
GO
ALTER TABLE [dbo].[Merchant_Period] CHECK CONSTRAINT [FK_Merchant_Period_Merchant]
GO
ALTER TABLE [dbo].[Order_Detail]  WITH CHECK ADD  CONSTRAINT [FK_Order_Detail_Order] FOREIGN KEY([order_id])
REFERENCES [dbo].[Order] ([id])
GO
ALTER TABLE [dbo].[Order_Detail] CHECK CONSTRAINT [FK_Order_Detail_Order]
GO
ALTER TABLE [dbo].[Order_Detail]  WITH CHECK ADD  CONSTRAINT [FKb8bg2bkty0oksa3wiq5mp5qnc] FOREIGN KEY([product_id])
REFERENCES [dbo].[Product] ([id])
GO
ALTER TABLE [dbo].[Order_Detail] CHECK CONSTRAINT [FKb8bg2bkty0oksa3wiq5mp5qnc]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK1mtsbur82frn64de7balymq9s] FOREIGN KEY([category_id])
REFERENCES [dbo].[Category] ([id])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK1mtsbur82frn64de7balymq9s]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK8pbf8x71cg7vn116ibwbkswq] FOREIGN KEY([product_unit_id])
REFERENCES [dbo].[Product_Unit] ([id])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK8pbf8x71cg7vn116ibwbkswq]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FKk47qmalv2pg906heielteubu7] FOREIGN KEY([merchant_id])
REFERENCES [dbo].[Merchant] ([id])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FKk47qmalv2pg906heielteubu7]
GO
ALTER TABLE [dbo].[Role_Permission]  WITH CHECK ADD  CONSTRAINT [FKa6jx8n8xkesmjmv6jqug6bg68] FOREIGN KEY([role_id])
REFERENCES [dbo].[Role] ([id])
GO
ALTER TABLE [dbo].[Role_Permission] CHECK CONSTRAINT [FKa6jx8n8xkesmjmv6jqug6bg68]
GO
ALTER TABLE [dbo].[Role_Permission]  WITH CHECK ADD  CONSTRAINT [FKf8yllw1ecvwqy3ehyxawqa1qp] FOREIGN KEY([permission_id])
REFERENCES [dbo].[Permission] ([id])
GO
ALTER TABLE [dbo].[Role_Permission] CHECK CONSTRAINT [FKf8yllw1ecvwqy3ehyxawqa1qp]
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD  CONSTRAINT [FKdl9dqp078pc03g6kdnxmnlqpc] FOREIGN KEY([role_id])
REFERENCES [dbo].[Role] ([id])
GO
ALTER TABLE [dbo].[User] CHECK CONSTRAINT [FKdl9dqp078pc03g6kdnxmnlqpc]
GO
USE [master]
GO
ALTER DATABASE [Midori_mart] SET  READ_WRITE 
GO
