package com.example.realestatemanager.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.realestatemanager.DAO.RealtyDao;
import com.example.realestatemanager.DAO.RealtyDao;
import com.example.realestatemanager.database.AppDataBase;
import com.example.realestatemanager.entities.Realty;

import java.util.List;

public class RealtyRepository {

    private RealtyDao realtyDao;
    private LiveData<List<Realty>> allRealties;

    // Constructeur
    public RealtyRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        realtyDao = db.realtyDao();
        allRealties = realtyDao.getAllRealties();
    }

    // Obtenir tous les biens immobiliers
    public LiveData<List<Realty>> getAllRealties() {
        return allRealties;
    }

    // Insérer un bien immobilier
    public void insert(Realty realty) {
        new InsertRealtyAsyncTask(realtyDao).execute(realty);
    }

    // Mettre à jour un bien immobilier
    public void update(Realty realty) {
        new UpdateRealtyAsyncTask(realtyDao).execute(realty);
    }

    // Supprimer un bien immobilier
    public void delete(Realty realty) {
        new DeleteRealtyAsyncTask(realtyDao).execute(realty);
    }

    // Classes AsyncTask pour les opérations en arrière-plan
    private static class InsertRealtyAsyncTask extends AsyncTask<Realty, Void, Void> {
        private RealtyDao realtyDao;

        private InsertRealtyAsyncTask(RealtyDao realtyDao) {
            this.realtyDao = realtyDao;
        }

        @Override
        protected Void doInBackground(final Realty... realties) {
            realtyDao.insert(realties[0]);
            return null;
        }
    }

    private static class UpdateRealtyAsyncTask extends AsyncTask<Realty, Void, Void> {
        private RealtyDao realtyDao;

        private UpdateRealtyAsyncTask(RealtyDao realtyDao) {
            this.realtyDao = realtyDao;
        }

        @Override
        protected Void doInBackground(final Realty... realties) {
            realtyDao.update(realties[0]);
            return null;
        }
    }

    private static class DeleteRealtyAsyncTask extends AsyncTask<Realty, Void, Void> {
        private RealtyDao realtyDao;

        private DeleteRealtyAsyncTask(RealtyDao realtyDao) {
            this.realtyDao = realtyDao;
        }

        @Override
        protected Void doInBackground(final Realty... realties) {
            realtyDao.delete(realties[0]);
            return null;
        }
    }
}

