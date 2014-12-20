/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rplt.studioMusik.dataPersewaan;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Agustinus Agri
 */
@Repository
public interface IPersewaanStudioMusikDAO<T> {
    
    public void simpanData(T pT);
    public String selesaiSewa(T pT);
    public boolean cekKetersediaanJadwal(T pT);
    public int hitungBiayaSewa(int pDurasi, String pKodeStudio);
    public String getGeneratedKodeSewa();
    public List<T> getDataListByMonth(String pDate);
    public byte[] cetakNota(String pKodeSewa, File pReportPath);
    
}
