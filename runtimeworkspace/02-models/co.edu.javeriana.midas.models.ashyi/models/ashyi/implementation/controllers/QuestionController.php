<?php

namespace App\Http\Controllers;

use App\Question as Question;

use Illuminate\Http\Request;

class QuestionController extends Controller
{
    public function questionAll(){
    	$question = Question::all(); 
    	return $question;
    }

	public function filter(){
		$question = Question::where()->get();
    	return $question;
	}
}

