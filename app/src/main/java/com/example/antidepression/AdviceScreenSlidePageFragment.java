package com.example.antidepression;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AdviceScreenSlidePageFragment extends Fragment {
    private final int position;
    private final String advice;

    public AdviceScreenSlidePageFragment(String advice, int position) {
        this.advice = advice;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.advice_fragment_screen_slide_page, container, false);
        TextView textView = (TextView) (rootView.findViewById(R.id.textView));
        textView.setText(advice);

        ImageView backgroundImage = (ImageView) (rootView.findViewById(R.id.backgound_image));
        FrameLayout parent = (FrameLayout) (rootView.findViewById(R.id.parent));
        String packageName = this.getContext().getApplicationInfo().packageName;
        int imageId = getResourseId(this.getContext(), "bg" + position, "drawable", packageName);
        //parent.setBackgroundResource(imageId);
        Uri uri = Uri.parse("android.resource://"+ packageName+"/"+imageId);
        backgroundImage.setImageURI(uri);
        return rootView;
    }

    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }
}