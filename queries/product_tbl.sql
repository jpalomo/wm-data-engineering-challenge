USE [wm]
GO

/****** Object:  Table [dbo].[product]    Script Date: 8/29/2018 8:51:42 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[product](
	[product_id] [varchar](255) NOT NULL,
	[vendor_id] [varchar](50) NULL,
	[thc] [float] NULL,
	[thca] [float] NULL,
	[cbd] [float] NULL,
	[cbda] [float] NULL) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


