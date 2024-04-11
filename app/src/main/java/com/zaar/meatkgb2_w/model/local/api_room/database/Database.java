package com.zaar.meatkgb2_w.model.local.api_room.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zaar.meatkgb2_w.model.local.api_room.dao.EnterpriseDao;
import com.zaar.meatkgb2_w.model.local.api_room.dao.ProductDao;
import com.zaar.meatkgb2_w.model.local.api_room.dao.RecordDao;
import com.zaar.meatkgb2_w.model.local.api_room.dao.ShopDao;
import com.zaar.meatkgb2_w.model.local.api_room.dao.UsrDataDao;
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.EnterpriseDb;
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ProductDb;
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.RecordDb;
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ShopDb;
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.UserDb;
import com.zaar.meatkgb2_w.utilities.types.AppConstStr;

@androidx.room.Database(
        version = 2,
        entities = {
                ShopDb.class,
                EnterpriseDb.class,
                UserDb.class,
                ProductDb.class,
                RecordDb.class
        },
        exportSchema = false
)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = AppConstStr.NAME_DATABASE.getValue();
    private static volatile Database INSTANCE;

    public abstract ShopDao shopDao();
    public abstract EnterpriseDao enterpriseDao();
    public abstract RecordDao recordDao();
    public abstract UsrDataDao usrDataDao();
    public abstract ProductDao productDao();

    private static final Object LOCK = new Object();

    public static Database getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    public static final Migration MIGRATION=new Migration(MIGRATION.startVersion, MIGRATION.endVersion) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//
//        }
//    }
}