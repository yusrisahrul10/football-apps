package com.dscunikom.android.footballmatchschedule.activity.main

import com.dscunikom.android.footballmatchschedule.utils.CoroutineContextProviderTest
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.api.TheSportsDBApi
import com.dscunikom.android.footballmatchschedule.model.Match
import com.dscunikom.android.footballmatchschedule.model.MatchResponse
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, CoroutineContextProviderTest())
    }

    @Test
    fun getMatchPast() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)
        val id = "4332"

        Mockito.`when`(gson.fromJson(apiRepository
            .doRequest(TheSportsDBApi.getMatchPast(id)),
            MatchResponse::class.java
        )
        ).thenReturn(response)

        presenter.getMatchPast(id)
        Thread.sleep(2000)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showMatchList(match)
    }

    @Test
    fun getMatchNext() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)
        val id = "4332"

        Mockito.`when`(gson.fromJson(apiRepository
            .doRequest(TheSportsDBApi.getMatchNext(id)),
            MatchResponse::class.java
        )
        ).thenReturn(response)

        presenter.getMatchNext(id)
        Thread.sleep(2000)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showMatchList(match)
    }
}