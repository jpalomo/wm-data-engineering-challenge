USE [wm]
GO

/****** Object:  Table [dbo].[batch]    Script Date: 8/29/2018 8:50:55 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[batch](
	[batch_id] [varchar](255) NOT NULL,
	[product_id] [varchar](255) NULL,
	[tested_at] [smalldatetime] NULL,
	[expires_at] [smalldatetime] NULL) ON [PRIMARY]
GO

SET ANSI_PADDING OFF
GO
