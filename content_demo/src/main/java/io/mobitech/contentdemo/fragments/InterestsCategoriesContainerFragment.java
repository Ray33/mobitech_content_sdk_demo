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

import java.util.ArrayList;
import java.util.List;

import io.mobitech.content.model.sphere.callbacks.AbsSphereUpdateCallback;
import io.mobitech.content.model.sphere.callbacks.ICategoryCallback;
import io.mobitech.content.model.sphere.callbacks.IInterestsCategoryCallback;
import io.mobitech.content.model.sphere.callbacks.IUpdateCallback;
import io.mobitech.content.model.sphere.categories.Categories;
import io.mobitech.content.model.sphere.categories.Category;
import io.mobitech.content.model.sphere.interests.CategoryInterest;
import io.mobitech.content.model.sphere.interests.InterestsCategoryResponse;
import io.mobitech.content.services.EntitiesServices;
import io.mobitech.content.services.InterestsServices;
import io.mobitech.contentdemo.R;


public class InterestsCategoriesContainerFragment extends Fragment {

    TextView mInterestsResult;
    List<Category> mUserCategories = new ArrayList<>();

    public InterestsCategoriesContainerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_interests_categories, container, false);

        mInterestsResult = (TextView) view.findViewById(R.id.interests_result);

        Button getUserCategoriesBtn = (Button) view.findViewById(R.id.interests_get_user_categories);
        getUserCategoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserCategories();
            }
        });

        Button deleteUserCategoryBtn = (Button) view.findViewById(R.id.interests_delete_user_category);
        deleteUserCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserCategory();
            }
        });

        Button addUserCategoryBtn = (Button) view.findViewById(R.id.interests_add_user_category);
        addUserCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserCategory();
            }
        });
        return view;
    }


    private void getUserCategories() {
        InterestsServices.getInterestsCategories(getContext(), new IInterestsCategoryCallback() {

            @Override
            public void execute(List<InterestsCategoryResponse> categories) {
                InterestsCategoryResponse categories_ = categories.get(0);
                mUserCategories.clear();
                String txt = "Got <b>" + categories_.getItems().size() + "</b> categories <br/><br/>";
                for (CategoryInterest c : categories_.getItems()) {
                    txt += c.getCategory().getName() + "<br/>";
                    mUserCategories.add(c.getCategory());
                }
                mInterestsResult.setText(Html.fromHtml(txt));
            }
        }, 0, 0);

    }

    private void deleteUserCategory() {
        //verify user categories are initiated
        if (mUserCategories.isEmpty()) {
            mInterestsResult.setText(Html.fromHtml("User category list is empty!<br/> Get all user categories or add new category"));
            return;
        }

        //delete the first category
        final Category categoryToDelete = mUserCategories.get(0);
        boolean isUserInterestedInTheCategory = false;
        InterestsServices.updateInterestsCategoryById(getContext(), new AbsSphereUpdateCallback() {

            @Override
            public void postUpdateSuccess(String msg) {
                mUserCategories.remove(categoryToDelete);
                String txt = "Category <b>" + categoryToDelete.getName() + "</b> delete was <b>successful</b>";
                mInterestsResult.setText(Html.fromHtml(txt));
            }

            @Override
            public void postUpdateFailure(String msg) {
                String txt = "Category <b>" + categoryToDelete.getName() + "</b> delete was <b>failure</b>";
                mInterestsResult.setText(Html.fromHtml(txt));

            }
        }, categoryToDelete.getId(), isUserInterestedInTheCategory);
    }


    private void addUserCategory() {
        //preparing a callback to handle the list of sites
        ICategoryCallback categoryCallback = new ICategoryCallback() {
            @Override
            public void execute(List<Categories> categoriesList) {
                //in this callback, choose one category and add it to user's category
                if (!categoriesList.isEmpty() && !categoriesList.get(0).getCategories().isEmpty()) {
                    //choose a random category out of available categories
                    final Category categoryToUpdate = categoriesList.get(0).getCategories().get((int) (Math.random() * (double) categoriesList.get(0).getCategories().size()) - 1);

                    //Optional: An update callback to handle result
                    IUpdateCallback updateSiteCallback = new AbsSphereUpdateCallback() {

                        @Override
                        public void postUpdateSuccess(String msg) {
                            String txt = "Category <b>" + categoryToUpdate.getName() + "</b> add was: <br/><b>successful</b>";
                            mInterestsResult.setText(Html.fromHtml(txt));
                        }

                        @Override
                        public void postUpdateFailure(String msg) {
                            String txt = "Category <b>" + categoryToUpdate.getName() + "</b> add was: <br/><b>failure</b>";
                            mInterestsResult.setText(Html.fromHtml(txt));
                        }
                    };
                    //update user site interest with a certain site
                    String categoryIdToAdd = categoryToUpdate.getId();
                    InterestsServices.updateInterestsCategoryById(getContext(), updateSiteCallback, categoryIdToAdd, true);
                }
            }
        };

        //get list of possible sites
        EntitiesServices.getCategories(getContext(), categoryCallback, 0, 0);
    }


}
