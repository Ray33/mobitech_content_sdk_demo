/*
 * Copyright (c) 2016. http://mobitech.io. All rights reserved.
 * Code is not permitted for commercial use w/o permission of Mobitech.io - support@mobitech.io .
 * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

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

import io.mobitech.content.model.sphere.callbacks.ICategoryCallback;
import io.mobitech.content.model.sphere.callbacks.ISitesCallback;
import io.mobitech.content.model.sphere.categories.Categories;
import io.mobitech.content.model.sphere.categories.Category;
import io.mobitech.content.model.sphere.sites.Site;
import io.mobitech.content.model.sphere.sites.SitesResponse;
import io.mobitech.content.services.EntitiesServices;
import io.mobitech.contentdemo.R;


public class EntitiesContainerFragment extends Fragment {

    TextView mEntitiesResult;

    public EntitiesContainerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_entities, container, false);

        mEntitiesResult = (TextView)view.findViewById(R.id.entities_result);

        //Get list of all categories that the platform supports.
        Button getCategoryBtn = (Button)view.findViewById(R.id.entities_get_categories);
        getCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategories();
            }
        });

        Button getSitesBtn = (Button) view.findViewById(R.id.entities_get_sites);
        getSitesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSites();
            }
        });

        return view;
    }


    //Get list of all categories that the platform supports.
    private void getCategories() {
        EntitiesServices.getCategories(getContext(), new ICategoryCallback() {
            @Override
            public void execute(List<Categories> categories) {
                Categories categories_ = categories.get(0);
                String txt = "Got <b>" + categories_.getCategories().size() + "</b> categories in the platform <br/><br/>";
                for (Category c : categories_.getCategories()){
                    txt += c.getName()+ "<br/>";
                }
                mEntitiesResult.setText(Html.fromHtml(txt));
            }
        }, 2000, 0);

    }

    private void getSites() {
        EntitiesServices.getSites(getContext(), new ISitesCallback() {
            @Override
            public void execute(List<SitesResponse> sites) {
                SitesResponse siteResponse = sites.get(0);
                String txt = "Got <b>" + siteResponse.getItems().size() + "</b> sites in the platform<br/><br/>";
                for (Site site : siteResponse.getItems()) {
                    txt += site.getName() + "<br/>";
                }
                mEntitiesResult.setText(Html.fromHtml(txt));
            }
        }, 2000, 0);
    }

}
