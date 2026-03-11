package com.lld.interviewquestions.cricbuzz.ScoreUpdater;


import com.lld.interviewquestions.cricbuzz.Inning.BallDetails;

public interface ScoreUpdaterObserver {

    public void update(BallDetails ballDetails);
}
