USE [wm]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[testresult](
	[batch_id] [varchar](255) NULL,
	[vendor_id] [varchar](255) NULL,
	[product_id] [varchar](255) NULL,
	[lab_id] [varchar](255) NULL,
	[state] [varchar](50) NULL,
	[tested_at] [varchar](255) NULL,
	[expires_at] [varchar](255) NULL,
	[thc] [decimal](18, 2) NULL,
	[thca] [decimal](18, 2) NULL,
	[cbd] [decimal](18, 2) NULL,
	[cbda] [decimal](18, 2) NULL,
	[potency] [decimal](18, 2) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO 