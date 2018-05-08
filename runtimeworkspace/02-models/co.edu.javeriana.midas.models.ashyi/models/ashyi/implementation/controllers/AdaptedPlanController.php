<?php

namespace App\Http\Controllers;

use App\AdaptedPlan as AdaptedPlan;

use Illuminate\Http\Request;

class AdaptedPlanController extends Controller
{
    public function adaptedplanAll(){
    	$adaptedplan = AdaptedPlan::all(); 
    	return $adaptedplan;
    }

	public function filter(){
		$adaptedplan = AdaptedPlan::where()->get();
    	return $adaptedplan;
	}
}

