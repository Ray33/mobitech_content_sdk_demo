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

import io.mobitech.content.model.sphere.callbacks.ICategoriesCallback;
import io.mobitech.content.model.sphere.callbacks.IDocumentsCallback;
import io.mobitech.content.model.sphere.callbacks.ISitesCallback;
import io.mobitech.content.model.sphere.categories.Categories;
import io.mobitech.content.model.sphere.categories.Category;
import io.mobitech.content.model.sphere.documents.Documents;
import io.mobitech.content.model.sphere.documents.Item;
import io.mobitech.content.model.sphere.sites.Site;
import io.mobitech.content.model.sphere.sites.SitesResponse;
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
        //Get document recommendation for user
        Button getRecommendedDoc = (Button) view.findViewById(R.id.recommendations_get_documents);
        getRecommendedDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocuments();
            }
        });

        //Get category recommendation for user
        Button getRecommendedCategoriesBtn = (Button) view.findViewById(R.id.recommendations_get_categories);
        getRecommendedCategoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategories();
            }
        });

        //Get sites recommendation for user
        Button getRecommendedSitesBtn = (Button) view.findViewById(R.id.recommendations_get_sites);
        getRecommendedSitesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSites();
            }
        });
        return view;
    }


    //Get document recommendation for user
    private void getDocuments() {
        RecommendationServices.getDocuments(getContext(), new IDocumentsCallback() {
            @Override
            public void execute(List<Documents> documents) {
                for (Documents d : documents){
                    String txt = "Got " + d.getItems().size() + " recommended documents  for the user<br/><br/>";
                    for (Item item : d.getItems()){
                        txt += item.toString() + "<br/>";
                    }
                    mRecommendationResult.setText(Html.fromHtml(txt));
                }
            }
        });
    }

    //Get category recommendation for user
    private void getCategories() {
        RecommendationServices.getCategories(getContext(), new ICategoriesCallback() {
            @Override
            public void execute(List<Categories> categories) {
                if (!categories.isEmpty()) {
                    Categories category = categories.get(0);
                    String txt = "Got " + category.getCategories().size() + " recommended categories for the user<br/><br/>";
                    for (Category c : category.getCategories()) {
                        txt += c.getName() + "<br/>";
                    }
                    mRecommendationResult.setText(Html.fromHtml(txt));
                }
            }
        });
    }


    //Get sites recommendation for user
    private void getSites() {
        RecommendationServices.getSites(getContext(), new ISitesCallback() {
            @Override
            public void execute(List<SitesResponse> sitesResponses) {
                if (!sitesResponses.isEmpty()) {
                    SitesResponse siteResponse = sitesResponses.get(0);
                    String txt = "Got " + siteResponse.getItems().size() + " recommended sites  for the user<br/><br/>";
                    for (Site site : siteResponse.getItems()) {
                        txt += site.getName() + "<br/>";
                    }
                    mRecommendationResult.setText(Html.fromHtml(txt));
                }
            }
        });
    }


}
