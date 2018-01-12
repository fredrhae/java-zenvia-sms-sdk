package com.zenvia.sms.sdk.base.models;

import java.util.ArrayList;
import java.util.List;

public class ZenviaSmsModel {

    protected transient List<String> errors = new ArrayList<>();

    /* Error printing methods */

    /**
     * @return {@link ZenviaSmsModel#errors errors} pretty printed.
     */
    public String getErrors(){
        StringBuilder errors = new StringBuilder();
        for(String error : this.errors) {
            errors.append("\n");
            errors.append(error);
        }
        return this.getClass().getSimpleName() + errors.toString();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZenviaSmsModel that = (ZenviaSmsModel) o;

        return errors != null ? errors.equals(that.errors) : that.errors == null;
    }

    @Override
    public int hashCode() {
        return errors != null ? errors.hashCode() : 0;
    }
}