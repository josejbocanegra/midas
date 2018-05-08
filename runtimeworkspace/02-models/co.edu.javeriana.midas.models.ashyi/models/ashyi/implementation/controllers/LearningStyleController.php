<?php

namespace App\Http\Controllers;

use App\LearningStyle as LearningStyle;

use Illuminate\Http\Request;

class LearningStyleController extends Controller
{
    public function learningstyleAll(){
    	$learningstyle = LearningStyle::all(); 
    	return $learningstyle;
    }

	public function filter(){
		$learningstyle = LearningStyle::where()->get();
    	return $learningstyle;
	}
}

