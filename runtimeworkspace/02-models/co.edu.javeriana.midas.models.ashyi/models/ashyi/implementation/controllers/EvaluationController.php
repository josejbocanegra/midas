<?php

namespace App\Http\Controllers;

use App\Evaluation as Evaluation;

use Illuminate\Http\Request;

class EvaluationController extends Controller
{
    public function evaluationAll(){
    	$evaluation = Evaluation::all(); 
    	return $evaluation;
    }

	public function filter(){
		$evaluation = Evaluation::where()->get();
    	return $evaluation;
	}
}

