<?php

namespace App\Http\Controllers;

use App\Student as Student;

use Illuminate\Http\Request;

class StudentController extends Controller
{
    public function studentAll(){
    	$student = Student::all(); 
    	return $student;
    }

	public function filter(){
		$student = Student::where()->get();
    	return $student;
	}
}

