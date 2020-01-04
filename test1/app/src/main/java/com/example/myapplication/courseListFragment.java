package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.*;

import com.example.myapplication.api.Client;
import com.example.myapplication.api.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link courseListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link courseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class courseListFragment extends Fragment {

    private static final String ARG_USER = "user";
    private User user;
    private List<Course> CourseList;
    List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
    private ListView listview;

    private OnFragmentInteractionListener mListener;

    public courseListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user userMsg.
     * @return A new instance of fragment courseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static courseListFragment newInstance(User user) {
        courseListFragment fragment = new courseListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();

        listview = (ListView)activity.findViewById(R.id.AllCourseList);
        //loadJSON();

        SimpleAdapter adapter = new SimpleAdapter(
                activity,
                listitem,
                R.layout.list_item,
                new String[]{"image_expense","description","name"},
                new int[]{R.id.cover,R.id.introduction,R.id.name});

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                String courseid = CourseList.get(position).getCourseid();
                String name = CourseList.get(position).getCoursename();
                String code = CourseList.get(position).getCode();
                String openDate = CourseList.get(position).getRemindtime();
                String description=CourseList.get(position).getDescription();
                bundle.putString("courseid", courseid);
                bundle.putString("name", name);
                bundle.putString("code", code);
                bundle.putString("openDate", openDate);
                bundle.putString("description", description);

                Intent intent = new Intent(activity,Introduction.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
        return inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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

    public void loadJSON() {
        try {
            Service request = Client.retrofit.create(Service.class);
            Call<List<Course>> call = request.getCourses();
            call.enqueue(new Callback<List<Course>>() {

                @Override
                public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                    CourseList = response.body();

                    //pd.hide();

                    for(int i = 0 ;i < CourseList.size(); i++){
                        Map<String, Object> map = new HashMap<String, Object>();
                        if(CourseList.get(i).getCourseid().equals("001")){
                            map.put("image_expense", R.drawable.course1);
                        }else if(CourseList.get(i).getCourseid().equals("002")){
                            map.put("image_expense", R.drawable.course2);
                        }else{
                            map.put("image_expense", R.drawable.course3);
                        }
                        map.put("name", CourseList.get(i).getCoursename());
                        map.put("description", CourseList.get(i).getDescription());
                        listitem.add(map);
                    }

                }

                @Override
                public void onFailure(Call<List<Course>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    /*Toast.makeText(homePage.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();*/
                    //pd.hide();
                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            /*Toast.makeText(courseListFragment.this, e.toString(), Toast.LENGTH_SHORT).show();*/
        }
    }
}
