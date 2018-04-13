/**
 * Label printing
 */

function retrieve(id) {
	var tdElem = document.getElementById ( id );
	if (tdElem != null) {
		return tdElem.value;
	} else {
		return "";
	}
}

function PrintLabels()
{
     var WinPrint = window.open('', '', 'letf=100,top=100,width=600,height=600');
     var id = retrieve("advertisment-id");
     var title =  retrieve("advertisment-title");
     var bookerName =  retrieve("booker-name");
     var bookerMail =  retrieve("booker-mail");
     var bookerPhone =  retrieve("booker-phone");
     WinPrint.document.write("<html><head></head><body>");
     WinPrint.document.write("<div>");
     WinPrint.document.write("Annonsens ID:<br />");
     WinPrint.document.write("<span style='font-weight:bold;font-size:larger'>" + id + "</span>");
     WinPrint.document.write("<br /><br />");
     WinPrint.document.write("Titel:<br />");
     WinPrint.document.write("<span style='font-weight:bold;font-size:larger'>" + title + "</span>");
     WinPrint.document.write("<br /><br />");
     WinPrint.document.write("Bokarens namn:<br />");
     WinPrint.document.write("<span style='font-weight:bold;font-size:larger'>" + bookerName + "</span>");
     WinPrint.document.write("<br /><br />");
     WinPrint.document.write("Bokarens epost:<br />");
     WinPrint.document.write("<span style='font-weight:bold;font-size:larger'>" + bookerMail + "</span>");
     WinPrint.document.write("<br /><br />");
     WinPrint.document.write("Bokarens telefon:<br />");
     WinPrint.document.write("<span style='font-weight:bold;font-size:larger'>" + bookerPhone + "</span>");
     
     WinPrint.document.write("</div></body>");
     WinPrint.document.close();
     WinPrint.focus();
     WinPrint.print();
}

