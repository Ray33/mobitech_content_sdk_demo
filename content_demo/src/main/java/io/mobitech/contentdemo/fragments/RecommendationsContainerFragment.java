package io.mobitech.contentdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import io.mobitech.content.model.sphere.callbacks.IDocumentsCallback;
import io.mobitech.content.model.sphere.documents.Documents;
import io.mobitech.content.model.sphere.documents.Item;
import io.mobitech.content.services.RecommendationServices;
import io.mobitech.contentdemo.R;


public class RecommendationsContainerFragment extends Fragment {


    TextView mRecommendationResult;

    public RecommendationsContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recomendations, container, false);

        mRecommendationResult = (TextView)view.findViewById(R.id.recommendations_result);

        Button getDoc = (Button)view.findViewById(R.id.recommendations_get_documents);
        getDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocuments();
            }
        });

//        //TBD
//        Button getCategories = (Button)view.findViewById(R.id.recommendations_get_categories);
//        Button getSites = (Button)view.findViewById(R.id.recommendations_get_sites);
        return view;
    }

    private void getDocuments() {
        RecommendationServices.getDocuments(RecommendationsContainerFragment.this.getContext(), new IDocumentsCallback() {
            @Override
            public void execute(List<Documents> documents) {
                for (Documents d : documents){
                    String txt = "Got " + d.getItems().size() + " documents <br/><br/>";
                    for (Item item : d.getItems()){
                        txt += item.toString() + "<br/>";
                    }
                    mRecommendationResult.setText(Html.fromHtml(txt));
                }
            }
        });
    }

}
