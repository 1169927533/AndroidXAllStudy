package com.example.a11699.comp_chat.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHAT_BEAN".
*/
public class ChatBeanDao extends AbstractDao<ChatBean, Long> {

    public static final String TABLENAME = "CHAT_BEAN";

    /**
     * Properties of entity ChatBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Sendid = new Property(1, String.class, "sendid", false, "sendid");
        public final static Property Sendname = new Property(2, String.class, "sendname", false, "sendname");
        public final static Property Sendimg = new Property(3, String.class, "sendimg", false, "sendimg");
        public final static Property Sendtime = new Property(4, String.class, "sendtime", false, "sendtime");
        public final static Property Receivetime = new Property(5, String.class, "receivetime", false, "receivetime");
        public final static Property Chatcontent = new Property(6, String.class, "chatcontent", false, "chatcontent");
        public final static Property WhichItem = new Property(7, int.class, "whichItem", false, "whichitem");
        public final static Property Receiveid = new Property(8, String.class, "receiveid", false, "receiveid");
        public final static Property Receivename = new Property(9, String.class, "receivename", false, "receivename");
        public final static Property Receiveimg = new Property(10, String.class, "receiveimg", false, "receiveimg");
    }


    public ChatBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ChatBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHAT_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ," + // 0: id
                "\"sendid\" TEXT," + // 1: sendid
                "\"sendname\" TEXT," + // 2: sendname
                "\"sendimg\" TEXT," + // 3: sendimg
                "\"sendtime\" TEXT," + // 4: sendtime
                "\"receivetime\" TEXT," + // 5: receivetime
                "\"chatcontent\" TEXT," + // 6: chatcontent
                "\"whichitem\" INTEGER NOT NULL ," + // 7: whichItem
                "\"receiveid\" TEXT," + // 8: receiveid
                "\"receivename\" TEXT," + // 9: receivename
                "\"receiveimg\" TEXT);"); // 10: receiveimg
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHAT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChatBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sendid = entity.getSendid();
        if (sendid != null) {
            stmt.bindString(2, sendid);
        }
 
        String sendname = entity.getSendname();
        if (sendname != null) {
            stmt.bindString(3, sendname);
        }
 
        String sendimg = entity.getSendimg();
        if (sendimg != null) {
            stmt.bindString(4, sendimg);
        }
 
        String sendtime = entity.getSendtime();
        if (sendtime != null) {
            stmt.bindString(5, sendtime);
        }
 
        String receivetime = entity.getReceivetime();
        if (receivetime != null) {
            stmt.bindString(6, receivetime);
        }
 
        String chatcontent = entity.getChatcontent();
        if (chatcontent != null) {
            stmt.bindString(7, chatcontent);
        }
        stmt.bindLong(8, entity.getWhichItem());
 
        String receiveid = entity.getReceiveid();
        if (receiveid != null) {
            stmt.bindString(9, receiveid);
        }
 
        String receivename = entity.getReceivename();
        if (receivename != null) {
            stmt.bindString(10, receivename);
        }
 
        String receiveimg = entity.getReceiveimg();
        if (receiveimg != null) {
            stmt.bindString(11, receiveimg);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChatBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sendid = entity.getSendid();
        if (sendid != null) {
            stmt.bindString(2, sendid);
        }
 
        String sendname = entity.getSendname();
        if (sendname != null) {
            stmt.bindString(3, sendname);
        }
 
        String sendimg = entity.getSendimg();
        if (sendimg != null) {
            stmt.bindString(4, sendimg);
        }
 
        String sendtime = entity.getSendtime();
        if (sendtime != null) {
            stmt.bindString(5, sendtime);
        }
 
        String receivetime = entity.getReceivetime();
        if (receivetime != null) {
            stmt.bindString(6, receivetime);
        }
 
        String chatcontent = entity.getChatcontent();
        if (chatcontent != null) {
            stmt.bindString(7, chatcontent);
        }
        stmt.bindLong(8, entity.getWhichItem());
 
        String receiveid = entity.getReceiveid();
        if (receiveid != null) {
            stmt.bindString(9, receiveid);
        }
 
        String receivename = entity.getReceivename();
        if (receivename != null) {
            stmt.bindString(10, receivename);
        }
 
        String receiveimg = entity.getReceiveimg();
        if (receiveimg != null) {
            stmt.bindString(11, receiveimg);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ChatBean readEntity(Cursor cursor, int offset) {
        ChatBean entity = new ChatBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sendid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sendname
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // sendimg
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // sendtime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // receivetime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // chatcontent
            cursor.getInt(offset + 7), // whichItem
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // receiveid
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // receivename
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // receiveimg
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChatBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSendid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSendname(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSendimg(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSendtime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setReceivetime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setChatcontent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setWhichItem(cursor.getInt(offset + 7));
        entity.setReceiveid(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setReceivename(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setReceiveimg(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ChatBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ChatBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChatBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}