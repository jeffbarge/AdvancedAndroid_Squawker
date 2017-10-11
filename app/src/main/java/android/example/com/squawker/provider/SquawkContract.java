/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package android.example.com.squawker.provider;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SquawkContract {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_AUTHOR_KEY = "authorKey";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_DATE = "date";


    // Topic keys as matching what is found in the database
    public static final String ASSER_KEY = "key_asser";
    public static final String CEZANNE_KEY = "key_cezanne";
    public static final String JLIN_KEY = "key_jlin";
    public static final String LYLA_KEY = "key_lyla";
    public static final String NIKITA_KEY = "key_nikita";
    public static final String TEST_ACCOUNT_KEY = "key_test";


    public static final String[] INSTRUCTOR_KEYS = {
            ASSER_KEY, CEZANNE_KEY, JLIN_KEY, LYLA_KEY, NIKITA_KEY
    };

    /**
     * Creates a SQLite SELECTION parameter that filters just the rows for the authors you are
     * currently following.
     */
    public static List<String> createSelectionForCurrentFollowers(SharedPreferences preferences) {
        List<String> instructors = new ArrayList<>();

        for (String key : INSTRUCTOR_KEYS) {
            if (preferences.getBoolean(key, false)) {
                instructors.add(key);
            }
        }
        return instructors;
    }
}