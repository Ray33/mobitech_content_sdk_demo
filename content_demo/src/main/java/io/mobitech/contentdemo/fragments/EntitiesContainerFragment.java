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
import io.mobitech.content.model.sphere.categories.Categories;
import io.mobitech.content.model.sphere.categories.Category;
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

        Button getCategoryBtn = (Button)view.findViewById(R.id.entities_get_categories);
        getCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategories();
            }
        });

        return view;
    }

    private void getCategories() {
        EntitiesServices.getCategories(getContext(), new ICategoryCallback() {
            @Override
            public void execute(List<Categories> categories) {
                Categories categories_ = categories.get(0);
                String txt = "Got <b>" + categories_.getCategories().size() + "</b> categories <br/><br/>";
                for (Category c : categories_.getCategories()){
                    txt += c.getName()+ "<br/>";
                }
                mEntitiesResult.setText(Html.fromHtml(txt));
            }
        }, 0, 0);

    }

}
