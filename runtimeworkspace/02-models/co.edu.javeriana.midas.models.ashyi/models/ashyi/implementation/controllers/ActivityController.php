<?php

namespace App\Http\Controllers;

use App\Activity as Activity;

use Illuminate\Http\Request;

class ActivityController extends Controller
{
    public function activityAll(){
    	$activity = Activity::all(); 
    	return $activity;
    }

	public function filter(){
		$activity = Activity::where()->get();
    	return $activity;
	}
}

