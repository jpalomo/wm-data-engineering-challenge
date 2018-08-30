USE [wm]
GO

/****** Object:  Table [dbo].[staging]    Script Date: 8/30/2018 8:10:38 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[staging](
	[batch_id] [varchar](255) NULL,
	[vendor_id] [varchar](255) NULL,
	[product_id] [varchar](255) NULL,
	[lab_id] [varchar](255) NULL,
	[state] [varchar](255) NULL,
	[tested_at] [varchar](255) NULL,
	[expires_at] [varchar](255) NULL,
	[thc] [varchar](255) NULL,
	[thca] [varchar] NULL,
	[cbd] [varchar] NULL,
	[cbda] [varchar] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

