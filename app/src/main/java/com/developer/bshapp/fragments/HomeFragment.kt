package com.developer.bshapp.fragments

import android.media.CamcorderProfile.get
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.developer.bshapp.R
import com.developer.bshapp.adapter.HomeRecyclerAdapter
import com.developer.bshapp.classes.URLs
import com.developer.bshapp.model.HomeNewsModel
import org.json.JSONObject
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var list: ArrayList<HomeNewsModel>
    lateinit var model: HomeNewsModel
    private lateinit var homeRecycler: RecyclerView
    lateinit var adapter: HomeRecyclerAdapter


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setupSlider(view)
        getdata()
        list = ArrayList<HomeNewsModel>()
        homeRecycler = view.findViewById<RecyclerView>(R.id.homeRecycler)
        homeRecycler.layoutManager = LinearLayoutManager(requireContext())

        adapter = HomeRecyclerAdapter(list, requireContext())
        homeRecycler.adapter = adapter

        return view
    }


    private fun getdata() {


        val queue = Volley.newRequestQueue(requireContext())
        val url: String = URLs.NEWS_URL

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
                { response ->
                    Log.d("API", response.toString())
                    try {

                        val respObj = JSONObject(response)
                        val success = respObj.getInt("status")
                        val jsonArray = respObj.getJSONArray("data")
                        if (success.equals(1)) {
                            for (i in 0 until jsonArray.length()) {
                                val item = jsonArray.getJSONObject(i)
                                val images = item.getString("imageName")
                                val headline = item.getString("heading")
                                val news = item.getString("news")
                                val mandal = item.getString("mandal")
                                val dates = item.getString("date")
                                val more = item.getString("more")
                                val uploader = item.getString("uploader")

                                model = HomeNewsModel(
                                        id,
                                        images,
                                        headline,
                                        news,
                                        mandal,
                                        dates,
                                        more,
                                        uploader
                                )
                                list.add(model)


                            }
                            Log.d("Hello", list.size.toString())
                            adapter.setData(list)
                        }

                    } catch (e: Exception) {
                        Log.d("Home", e.toString())
                    }

                },
                {
                    Log.d("API", "that didn't work")
                })
        queue.add(stringReq)
    }
    private fun setupSlider(view: View) {
        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(
                SlideModel(R.drawable.sliderimage)
        )
        imageList.add(
                SlideModel(
                        R.drawable.sliderimage1
                )
        )
        imageList.add(SlideModel(R.drawable.sliderimage2))
        imageList.add(SlideModel(R.drawable.sliderimage3))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
        for (i in 0 until imageList.size) {
            imageList.get(i).scaleType
        }

    }


}