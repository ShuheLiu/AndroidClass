package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.*;
import android.widget.Toast;

import com.example.myapplication.api.Client;
import com.example.myapplication.api.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MediaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediaFragment extends Fragment {
    private static final String ARG_USER = "user";
    private User user;
    private List<material> MaterialList;
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public MediaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user user.
     * @return A new instance of fragment MediaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MediaFragment newInstance(User user) {
        MediaFragment fragment = new MediaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();

        recyclerView = (RecyclerView)activity.findViewById(R.id.mediaList);
        initData();

        final MediaAdapter adapter = new MediaAdapter(MaterialList);
        adapter.setOnItemClickListener(new MediaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(MaterialList.get(position).getMaterialType().equals("lecture video")) {
                    Intent intent = new Intent(activity,Video.class);
                    intent.putExtra("mid", MaterialList.get(position).getId());
                    activity.startActivity(intent);
                }else if(MaterialList.get(position).getMaterialType().equals("image")){
                    Intent intent = new Intent(activity,Image.class);
                    intent.putExtra("mid", MaterialList.get(position).getId());
                    activity.startActivity(intent);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User)getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void initData(){
        try {
            Service request = Client.retrofit.create(Service.class);
            Call<List<material>> call = request.getMaterial();
            call.enqueue(new Callback<List<material>>() {

                @Override
                public void onResponse(Call<List<material>> call, Response<List<material>> response) {
                    MaterialList = response.body();
                }

                @Override
                public void onFailure(Call<List<material>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    //pd.hide();
                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

    }
}
