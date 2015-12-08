#include "MyForm.h"
#include "image.h"
#include <msclr\marshal_cppstd.h> //for convert string^ to string
#include <string>

using namespace System;
using namespace System::Windows::Forms;
using namespace std;


//[STAThread]
void Main(array<String^>^ args)
{
	
	Application::EnableVisualStyles();
	Application::SetCompatibleTextRenderingDefault(false);

	NikVi::MyForm form;
	Application::Run(%form);
}


namespace NikVi
{
	
	
	void MyForm::button1_Click(System::Object^  sender, System::EventArgs^  e) 
	{
		// set button state
		this->button2->Enabled = true;
		this->btnOpen->Enabled = false;
	}

	void MyForm::button2_Click(System::Object^  sender, System::EventArgs^  e)
	{
		// change the button states.
		this->button2->Enabled = false;
		this->btnOpen->Enabled = true;
	}

	void MyForm::btnOpen_Click(System::Object^  sender, System::EventArgs^  e)
	{	
		string copyFileNameToString = "";
		char *copyFileNameToChar;

		//read the path of image(FileName).
		this->openFileDialog1->Filter = "BMP|*.bmp";
		if (this->openFileDialog1->ShowDialog() == System::Windows::Forms::DialogResult::OK)
		{
			this->txbFileName->Text = this->openFileDialog1->FileName;
			this->txbSaveName->Text = this->openFileDialog1->SafeFileName;
			//this->pictureBox1->Load(this->openFileDialog1->FileName);

			//convert name of file from string^ to char*.
			copyFileNameToString = msclr::interop::marshal_as<std::string>(this->openFileDialog1->FileName);
			copyFileNameToChar = new char[copyFileNameToString.length() + 1];
			strcpy(copyFileNameToChar, copyFileNameToString.c_str());

		}	
		delete[] copyFileNameToChar;
	}
}
