package com.example.realestatemanager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.realestatemanager.dao.RealEstateDao;
import com.example.realestatemanager.database.AppDatabase;
import com.example.realestatemanager.model.RealEstate;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

import android.os.Build;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class RealEstateDaoTest {

    private AppDatabase db;
    private RealEstateDao dao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.realtyListDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void writeRealEstateAndReadInList() throws InterruptedException {
        RealEstate realEstate = new RealEstate();
        // Configurez votre objet realEstate ici
        realEstate.setId(1);
        dao.insert(realEstate); // Correction ici

        LiveData<List<RealEstate>> liveData = dao.getAll();
        List<RealEstate> realEstateList = getValue(liveData);

        assertEquals(realEstateList.get(0).getId(), realEstate.getId());
        // Vous pouvez ajouter plus d'assertions ici pour tester d'autres champs
    }

    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                data[0] = t;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}
