<?php

namespace App\Http\Controllers;

use App\KnowledgeItem as KnowledgeItem;

use Illuminate\Http\Request;

class KnowledgeItemController extends Controller
{
    public function knowledgeitemAll(){
    	$knowledgeitem = KnowledgeItem::all(); 
    	return $knowledgeitem;
    }

	public function filter(){
		$knowledgeitem = KnowledgeItem::where()->get();
    	return $knowledgeitem;
	}
}

