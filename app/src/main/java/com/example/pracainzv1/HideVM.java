package com.example.pracainzv1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HideVM extends ViewModel {
    public MutableLiveData<ContenerFile> contenerFileMutableLiveData;
    private String filePath="/data/data/com.example.pracainzv1/files/foo.txt";
    private ContenerFile contenerFile;


    public HideVM() {
        if (contenerFileMutableLiveData == null){
            contenerFileMutableLiveData = new MutableLiveData<ContenerFile>();
        }
    }

    public void setContenerFile(){
        if (contenerFile == null) {
            contenerFile = new ContenerFile("Test",
                    "/data/data/com.example.pracainzv1/files/foo.txt",
                    (byte) 12,
                    12);
        }
//        var temp = contenerFileMutableLiveData.getValue();
//        if (temp == null) {
//            contenerFileMutableLiveData.setValue(contenerFile);
//        }
    }


}
