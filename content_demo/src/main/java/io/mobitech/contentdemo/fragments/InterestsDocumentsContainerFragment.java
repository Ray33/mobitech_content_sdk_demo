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
import io.mobitech.content.model.sphere.callbacks.IDocumentsCallback;
import io.mobitech.content.model.sphere.callbacks.IInterestsDocumentCallback;
import io.mobitech.content.model.sphere.documents.Document;
import io.mobitech.content.model.sphere.documents.Documents;
import io.mobitech.content.model.sphere.interests.DocumentInterest;
import io.mobitech.content.model.sphere.interests.InterestsDocumentResponse;
import io.mobitech.content.services.InterestsServices;
import io.mobitech.content.services.RecommendationServices;
import io.mobitech.contentdemo.R;


public class InterestsDocumentsContainerFragment extends Fragment {

    TextView mInterestsResult;
    List<Document> mUserDocuments = new ArrayList<>();

    public InterestsDocumentsContainerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_interests_documents, container, false);

        mInterestsResult = (TextView)view.findViewById(R.id.interests_result);

        Button getUserDocumentsBtn = (Button) view.findViewById(R.id.interests_get_user_documents);
        getUserDocumentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserDocuments();
            }
        });

        Button deleteUserDocumentBtn = (Button) view.findViewById(R.id.interests_delete_user_documents);
        deleteUserDocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserDocuments();
            }
        });

        Button addUserDocumentBtn = (Button) view.findViewById(R.id.interests_add_user_documents);
        addUserDocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserDocuments();
            }
        });

        Button showADocumentBtn = (Button) view.findViewById(R.id.show_a_document);
        showADocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDocumentFromUserDocuments();
            }
        });


        return view;
    }

    private void getUserDocuments() {
        InterestsServices.getInterestsDocuments(getContext(), new IInterestsDocumentCallback() {

            @Override
            public void execute(List<InterestsDocumentResponse> documentResponses) {
                if (documentResponses.isEmpty()) {
                    return;
                }

                InterestsDocumentResponse documentResponse = documentResponses.get(0);
                mUserDocuments.clear();
                String txt = "Got <b>" + documentResponse.getItems().size() + "</b> documents  <br/><br/>";
                for (DocumentInterest d : documentResponse.getItems()) {
                    txt += d.getDocument().getTitle() + "<br/>";
                    mUserDocuments.add(d.getDocument());
                }
                mInterestsResult.setText(Html.fromHtml(txt));
            }
        });

    }

    private void deleteUserDocuments() {
        //verify user categories are initiated
        if (mUserDocuments.isEmpty()) {
            mInterestsResult.setText(Html.fromHtml("User document list is empty!<br/>Click to Get all user documents or add new category"));
            return;
        }

        //delete the first category
        final Document documentToDelete = mUserDocuments.get(0);
        boolean isUserInterestedInTheCategory = false;
        InterestsServices.updateInterestsDocumentById(getContext(), new AbsSphereUpdateCallback() {

            @Override
            public void postUpdateSuccess(String msg) {
                mUserDocuments.remove(documentToDelete);
                String txt = "Document <b>" + documentToDelete.getTitle() + "</b> delete was <b> successful </b>";
                mInterestsResult.setText(Html.fromHtml(txt));
            }

            @Override
            public void postUpdateFailure(String msg) {
                String txt = "Document <b>" + documentToDelete.getTitle() + "</b> delete was <b> failure </b>";
                mInterestsResult.setText(Html.fromHtml(txt));
            }
        }, documentToDelete.getId(), isUserInterestedInTheCategory);
    }


    private void addUserDocuments() {
        //preparing a callback to handle the list of documents
        IDocumentsCallback documentsCallback = new IDocumentsCallback() {
            @Override
            public void execute(List<Documents> documents) {
                //in this callback, choose one category and add it to user's category
                if (!documents.isEmpty() && !documents.get(0).getItems().isEmpty()) {
                    //choose a random document out of available documents
                    int randomDocumentPosition = (int) (Math.random() * (double) documents.get(0).getItems().size()) - 1;
                    final Document documentToUpdate = documents.get(0).getItems().get(randomDocumentPosition).getDocument();

                    AbsSphereUpdateCallback updateDocumentCallback = new AbsSphereUpdateCallback() {

                        @Override
                        public void postUpdateSuccess(String msg) {
                            String txt = "Document <b>" + documentToUpdate.getTitle() + "</b> add status is: <br/><b>successful</b>";
                            mInterestsResult.setText(Html.fromHtml(txt));
                        }

                        @Override
                        public void postUpdateFailure(String msg) {
                            String txt = "Document <b>" + documentToUpdate.getTitle() + "</b> add status is: <br/><b>failure</b>";
                            mInterestsResult.setText(Html.fromHtml(txt));
                        }
                    };
                    //update user site interest with a certain site
                    String categoryIdToAdd = documentToUpdate.getId();
                    InterestsServices.updateInterestsDocumentById(getContext(), updateDocumentCallback, categoryIdToAdd, true);
                }
            }
        };

        //get list of possible recommended documents to user
        RecommendationServices.getDocuments(getContext(),null, documentsCallback);
    }


    private void displayDocumentFromUserDocuments() {
        InterestsServices.getInterestsDocuments(getContext(), new IInterestsDocumentCallback() {
            @Override
            public void execute(List<InterestsDocumentResponse> interestsDocumentResponses) {
                if (interestsDocumentResponses.isEmpty() || interestsDocumentResponses.get(0).getItems().isEmpty()) {
                    //user is not associated to any document - display relevant message
                    String txt = "No document is associated to user.";
                    mInterestsResult.setText(Html.fromHtml(txt));
                } else {
                    List<DocumentInterest> interestsDocument = interestsDocumentResponses.get(0).getItems();
                    mInterestsResult.setText(Html.fromHtml(interestsDocument.get(0).getDocument().toString()));
                }
            }
        });
    }

}
