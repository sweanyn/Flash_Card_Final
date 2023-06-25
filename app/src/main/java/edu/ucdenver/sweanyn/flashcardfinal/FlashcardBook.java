package edu.ucdenver.sweanyn.flashcardfinal;

import java.io.Serializable;

public class FlashcardBook implements Serializable {
    private String name;

    public FlashcardBook(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    // Add other methods as needed
}
