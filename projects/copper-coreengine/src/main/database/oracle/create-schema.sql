--
-- COP_WORKFLOW_INSTANCE
--
create table COP_WORKFLOW_INSTANCE  (
   ID           		VARCHAR2(128CHAR)               not null,
   STATE                NUMBER(1)                       not null,
   PRIORITY             NUMBER(2)                       not null,
   LAST_MOD_TS          TIMESTAMP                       not null,
   PPOOL_ID      		VARCHAR2(32CHAR)				not null,
   OBJECT_STATE			VARCHAR2(4000CHAR)				null,
   LONG_OBJECT_STATE	CLOB							null,
   DATA					VARCHAR2(4000CHAR)				null,
   LONG_DATA			CLOB							null,
   CS_WAITMODE			NUMBER(1),
   MIN_NUMB_OF_RESP		NUMBER(10),
   NUMB_OF_WAITS		NUMBER(10),
   TIMEOUT				TIMESTAMP,
   CREATION_TS			TIMESTAMP						not null,
   CLASSNAME			VARCHAR2(512CHAR)				not null,
   constraint PK_COP_WORKFLOW_INSTANCE primary key (ID)
)
LOB(LONG_DATA) STORE AS SECUREFILE
LOB(LONG_OBJECT_STATE) STORE AS SECUREFILE
INITRANS 5;

--
-- COP_WORKFLOW_INSTANCE_ERROR
--
create table COP_WORKFLOW_INSTANCE_ERROR (
   WORKFLOW_INSTANCE_ID		VARCHAR2(128CHAR) not null,
   EXCEPTION				CLOB			  not null,
   ERROR_TS     	   		TIMESTAMP(3)      not null
);
 
create index IDX_COP_WFID_WFID on COP_WORKFLOW_INSTANCE_ERROR (
   WORKFLOW_INSTANCE_ID
);

--
-- COP_RESPONSE
--
create table COP_RESPONSE  (
   RESPONSE_ID			VARCHAR2(128CHAR) 	not null,
   CORRELATION_ID		VARCHAR2(128CHAR) 	not null,
   RESPONSE_TS			TIMESTAMP 			not null,
   RESPONSE				VARCHAR2(4000CHAR),
   LONG_RESPONSE		CLOB,
   RESPONSE_TIMEOUT		TIMESTAMP,
   RESPONSE_META_DATA	VARCHAR2(4000CHAR),
   constraint PK_COP_RESPONSE primary key (RESPONSE_ID) using index storage (buffer_pool keep)
)
LOB(LONG_RESPONSE) STORE AS SECUREFILE;

create index IDX_COP_RESP_CID on COP_RESPONSE (
   CORRELATION_ID
)
storage (buffer_pool keep);

 
--
-- COP_WAIT
--
create table COP_WAIT (
   	CORRELATION_ID			VARCHAR2(128CHAR) 	not null,
   	WORKFLOW_INSTANCE_ID  	VARCHAR2(128CHAR) 	not null,
	MIN_NUMB_OF_RESP		NUMBER(10) 			not null,
	TIMEOUT_TS				TIMESTAMP(2),
   	WFI_ROWID				ROWID 				not null,
   	STATE					NUMBER(1) 			not null,
    PRIORITY            	NUMBER(2) 			not null,
    PPOOL_ID      			VARCHAR2(32CHAR) 	not null,
    constraint PK_COP_WAIT primary key (CORRELATION_ID),
    constraint FK_COP_WAIT_REFERENCE_BP foreign key (WORKFLOW_INSTANCE_ID) references COP_WORKFLOW_INSTANCE (ID)
)
organization index storage (buffer_pool keep);


create index IDX_COP_WAIT_WFI_ID on COP_WAIT (
   WORKFLOW_INSTANCE_ID
)
storage (buffer_pool keep);

--
-- COP_QUEUE
--
create table COP_QUEUE (
   PPOOL_ID      		VARCHAR2(32CHAR)				not null,
   PRIORITY             NUMBER(2)                       not null,
   LAST_MOD_TS          TIMESTAMP                       not null,
   WFI_ROWID			ROWID							not null,
   ENGINE_ID			VARCHAR2(16CHAR)				null,
   constraint PK_COP_QUEUE primary key (PPOOL_ID,PRIORITY,WFI_ROWID)
)
organization index 
storage (buffer_pool keep)
compress 2;

--
-- COP_AUDIT_TRAIL_EVENT
--
create table COP_AUDIT_TRAIL_EVENT (
	SEQ_ID 					NUMBER(19) 			NOT NULL,
	OCCURRENCE				TIMESTAMP 			NOT NULL,
	CONVERSATION_ID 		VARCHAR2(64CHAR) 	NOT NULL,
	LOGLEVEL				NUMBER(2) 			NOT NULL,
	CONTEXT					VARCHAR2(128CHAR) 	NOT NULL,
	INSTANCE_ID				VARCHAR2(128CHAR) 	NULL,
	CORRELATION_ID 			VARCHAR2(128CHAR) 	NULL,
	TRANSACTION_ID 			VARCHAR2(128CHAR) 	NULL,
	LONG_MESSAGE 			CLOB 				NULL,
	MESSAGE_TYPE			VARCHAR2(256CHAR) 	NULL
);

CREATE SEQUENCE COP_SEQ_AUDIT_TRAIL CACHE 1000;


--
-- COP_ADAPTERCALL
--
CREATE TABLE COP_ADAPTERCALL ("WORKFLOWID"  VARCHAR2(128CHAR) NOT NULL,
                          "ENTITYID"    VARCHAR2(128CHAR) NOT NULL,
                          "ADAPTERID"   VARCHAR2(256CHAR) NOT NULL,
                          "PRIORITY"    NUMBER(19,0) NOT NULL,
                          "DEFUNCT"     CHAR(1) DEFAULT '0' NOT NULL ,
                          "DEQUEUE_TS"  TIMESTAMP , 
                          "METHODDECLARINGCLASS" VARCHAR2(1024CHAR)  NOT NULL,
                          "METHODNAME" VARCHAR2(1024CHAR)  NOT NULL,
                          "METHODSIGNATURE" VARCHAR2(2048CHAR)  NOT NULL,
                          "ARGS" CLOB,
                          CONSTRAINT PK_ADAPTERCLASS PRIMARY KEY (ADAPTERID, WORKFLOWID, ENTITYID));

CREATE INDEX COP_IDX_ADAPTERCALL ON COP_ADAPTERCALL(ADAPTERID, PRIORITY);

