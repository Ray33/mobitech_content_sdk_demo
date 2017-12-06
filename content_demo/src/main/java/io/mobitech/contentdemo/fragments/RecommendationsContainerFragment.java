/*
 * Copyright (c) 2016. http://mobitech.io. All rights reserved.
 * Code is not permitted for commercial use w/o permission of Mobitech.io - support@mobitech.io .
 * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package io.mobitech.contentdemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import io.mobitech.content.model.mobitech.Document;
import io.mobitech.content.services.RecommendationService;
import io.mobitech.content.services.api.callbacks.ContentCallback;
import io.mobitech.contentdemo.R;
import io.mobitech.contentdemo.util.SharedPreferencesUtil;

public class RecommendationsContainerFragment extends Fragment {


    TextView mRecommendationResult;

    private RecommendationService recommendationService;

    public RecommendationsContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
        recommendationService = RecommendationService.build(getContext(), "TESTC36B5A", sharedPreferencesUtil.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recomendations, container, false);

        mRecommendationResult = (TextView)view.findViewById(R.id.recommendations_result);
        //Get document recommendation for user
        Button getRecommendedDoc = (Button) view.findViewById(R.id.recommendations_get_documents);
        getRecommendedDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocuments();
            }
        });

        return view;
    }

    /**
     * Get document recommendation for the user
     */
    private void getDocuments() {

        recommendationService.getOrganicContent(null, null, new ContentCallback<List<Document>>() {
            @Override
            public void processResult(List<Document> contentResult, Context context) {

                StringBuilder txtBuilder = new StringBuilder("Got " + contentResult.size() +
                        " recommended documents  for the user<br/><br/>");

                for (Document document : contentResult) {
                    txtBuilder.append(" ** ").append(document.getTitle()).append("<br/>");
                }

                mRecommendationResult.setText(Html.fromHtml(txtBuilder.toString()));
            }
        });
    }
}
