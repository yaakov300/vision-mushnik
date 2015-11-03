#include "MyForm.h"

using namespace System;
using namespace System::Windows::Forms;

[STAThread]
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
		this->button1->Enabled = false;
	}
	void MyForm::button2_Click(System::Object^  sender, System::EventArgs^  e)
	{
		// change the button states.
		this->button2->Enabled = false;
		this->button1->Enabled = true;
	}
}
