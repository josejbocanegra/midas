<?php

namespace App\Http\Controllers;

use App\KnowledgeLevel as KnowledgeLevel;

use Illuminate\Http\Request;

class KnowledgeLevelController extends Controller
{
    public function knowledgelevelAll(){
    	$knowledgelevel = KnowledgeLevel::all(); 
    	return $knowledgelevel;
    }

	public function filter(){
		$knowledgelevel = KnowledgeLevel::where()->get();
    	return $knowledgelevel;
	}
}

