package com.example.ownproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ownproject.Constant;
import com.example.ownproject.R;
import com.example.ownproject.util.CharacterHelper;
import com.example.ownproject.util.FaceHelper;
import com.example.ownproject.util.StarHelper;
import com.example.ownproject.util.WeatherHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        bindView(root);
        return root;
    }

    private void bindView(View root) {
        ImageButton starButton = root.findViewById(R.id.star);
        starButton.setOnClickListener(v -> showInputDialog(Constant.Type.STAR));
        ImageButton weatherButton = root.findViewById(R.id.weather);
        weatherButton.setOnClickListener(v -> showInputDialog(Constant.Type.WEATHER));
        ImageButton characterButton = root.findViewById(R.id.character);
        characterButton.setOnClickListener(v -> showInputDialog(Constant.Type.CHARACTER));
        ImageButton faceButton = root.findViewById(R.id.face);
        faceButton.setOnClickListener(v -> showInputDialog(Constant.Type.FACE));
    }

    private void showInputDialog(int type) {
        switch (type) {
            case 1:
                StarHelper starHelper = new StarHelper(getActivity(), this);
                starHelper.help();
                break;
            case 2:
                WeatherHelper weatherHelper = new WeatherHelper(getActivity(), this);
                weatherHelper.help();
                break;
            case 3:
                CharacterHelper characterhelper = new CharacterHelper(getActivity(), this);
                characterhelper.help();
                break;
            case 4:
                FaceHelper facehelper = new FaceHelper(getActivity(), this);
                facehelper.help();
                break;
            default:
                break;
        }
    }

}
