package com.dscunikom.android.footballmatchschedule.activity.matchdetail

import com.dscunikom.android.footballmatchschedule.utils.CoroutineContextProviderTest
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.api.TheSportsDBApi
import com.dscunikom.android.footballmatchschedule.model.Team
import com.dscunikom.android.footballmatchschedule.model.TeamResponse
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest {

    @Mock
    private
    lateinit var viewMatch: MatchDetailView

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    private lateinit var presenterMatch: MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenterMatch = MatchDetailPresenter(viewMatch, apiRepository, gson, CoroutineContextProviderTest())
    }

    @Test
    fun getTeamDetail() {
        val team: MutableList<Team> = mutableListOf()
        val response = TeamResponse(team)
        val id = "4332"

        Mockito.`when`(gson.fromJson(apiRepository
            .doRequest(TheSportsDBApi.getTeam(id)),
            TeamResponse::class.java
        )
        ).thenReturn(response)

        presenterMatch.getTeamDetail(id, id)
        Thread.sleep(2000)

        Mockito.verify(viewMatch).showLoading()
        Mockito.verify(viewMatch).hideLoading()
        Mockito.verify(viewMatch).showTeamDetail(response.teams, response.teams)
    }
}