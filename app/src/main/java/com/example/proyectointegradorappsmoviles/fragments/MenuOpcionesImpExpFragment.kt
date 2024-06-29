package com.example.proyectointegradorappsmoviles.fragments

//import com.example.proyectointegradorappsmoviles.ARG_PARAM1
//import com.example.proyectointegradorappsmoviles.ARG_PARAM2
//import com.example.proyectointegradorappsmoviles.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectointegradorappsmoviles.R
import com.example.proyectointegradorappsmoviles.entities.Cliente
import com.example.proyectointegradorappsmoviles.viewmodels.ClienteViewmodel
import com.example.proyectointegradorappsmoviles.viewmodels.VentaViewModel
import com.opencsv.CSVReaderBuilder
import com.opencsv.CSVWriter
import com.opencsv.CSVWriterBuilder
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.URI
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


/**
 * A simple [Fragment] subclass.
 * Use the [MenuOpcionesImpExpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuOpcionesImpExpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var clienteViewModel: ClienteViewmodel
    private lateinit var ventaViewModel: VentaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val a: Activity

        if (context is Activity) {
            a = context
        }

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                // PERMISSION GRANTED
                val a = 1
            } else {
                // PERMISSION NOT GRANTED
                val a = 1
            }
        }

        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_menu_opciones_imp_exp, container, false)

        clienteViewModel = ViewModelProvider(this).get(ClienteViewmodel::class.java)
        ventaViewModel = ViewModelProvider(this).get(VentaViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.botonExportarVentas).setOnClickListener()
        {
            //findNavController().navigate(R.id.action_menuPrincipalFragment_to_registrarVentaFragment)
            ExportarVentas2()
        }

        view.findViewById<Button>(R.id.botonImportarClientes).setOnClickListener()
        {
            //findNavController().navigate(R.id.action_menuPrincipalFragment_to_menuOpcionesImpExpFragment)
            ImportarClientes()


            //startActivityForResult(Intent.createChooser(intent, "Seleccionar Archivo de Clientes"), 100)
        }
    }


    fun ExportarVentas2()
    {
            val CREATE_FILE = 1

            val dateFormat: DateFormat = SimpleDateFormat("ddMMyyyy_HHmmss")
            val date: Date = Calendar.getInstance().time;
            val dateString : String = dateFormat.format(date).replace(":", ".")+".csv"

            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/comma-separated-values"
                putExtra(Intent.EXTRA_TITLE, "ventas_domicilio_" + dateString)
            }
        startActivityForResult(intent, CREATE_FILE)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1)
        {


            if(resultCode == RESULT_OK)
            {
                val uriArchivo : Uri? = data?.data
                val outputStream: OutputStream = requireActivity().contentResolver.openOutputStream(uriArchivo!!)!!
                //var reader = CSVWriter(FileWriter( File( URI(uriArchivo))))
                outputStream.write("ID,Cliente,Cantidad,Fecha y Hora,Pagada,Total\n".encodeToByteArray())

                ventaViewModel.repositorio.listadoCompletoVentas.observe(viewLifecycleOwner)
                {
                    lista->

                    for(venta in lista)
                    {
                        var renglonVenta : String = venta.id.toString() + "," + venta.nombreCliente + "," + venta.cantidad.toString() + "," + venta.FechaHora + "," + venta.pagado.toString() + ","+ venta.total.toString() +"\n"

                        outputStream.write(renglonVenta.encodeToByteArray())
                    }

                    outputStream.close()
                    ventaViewModel.borrarTodasVentas()

                    Toast.makeText(context, "Ventas exportadas exitosamente", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    fun ExportarVentas()
    {

        var permissions = mutableListOf<String>()
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        requestPermissions(permissions.toTypedArray(), 0)

        /*if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {


            ActivityCompat.requestPermissions(MainActivity(),
                )
        }*/


        val dateFormat: DateFormat = SimpleDateFormat("ddMMyyyy_HHmmss")
        val date: Date = Calendar.getInstance().time;
        val dateString : String = dateFormat.format(date).replace(":", ".")+".csv"

        var nuevoArchivoVentas : File = File(Environment.getExternalStorageDirectory(),"ventas_domicilio_" + dateString)

        if(!nuevoArchivoVentas.exists())
        {
            nuevoArchivoVentas.mkdirs()
        }

        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/comma-separated-values"
            putExtra(Intent.EXTRA_TITLE, "ventas_domicilio_" + dateString)

            val REQUEST_CODE_ARBITRARY = 1
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)

            // will trigger exception if no  appropriate category passed
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            // or whatever mimeType you want
            intent.setType("*text/plain")
            intent.putExtra(Intent.EXTRA_TITLE, "prueba")


        }

        /*var launcherActividadFileManager = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            if(result.resultCode == Activity.RESULT_OK) {

            }
        }

        launcherActividadFileManager.launch(intent)*/




        /*var writer = CSVWriter(FileWriter(nuevoArchivoVentas))

        val data: MutableList<Array<String>> = ArrayList()
        data.add(arrayOf("ID", "Cliente", "Cantidad", "Fecha y Hora", "Pagado", "Total Venta"))

        ventaViewModel.repositorio.listadoCompletoVentas.observe(viewLifecycleOwner)
        {
            lista ->

            for (venta in lista)
            {
                data.add(arrayOf(venta.id.toString(),venta.nombreCliente, venta.cantidad.toString(), venta.FechaHora, venta.pagado.toString(), venta.total.toString()))
            }
        }

        writer.writeAll(data) // data is adding to csv

        writer.close()

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.setType("text/plain")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("email@example.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "body text")

        val file: File = File("VENTAS DOMICILIO " + dateString)
        val uri = Uri.fromFile(file)
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"))*//**/

    }

    fun ImportarClientes() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.setType("text/comma-separated-values")
        launcherActividadFileManager.launch(intent)

    }

    var launcherActividadFileManager = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if(result.resultCode == Activity.RESULT_OK)
        {
            val uriArchivo = result.data?.data

            val inputStream: InputStream = requireActivity().contentResolver.openInputStream(uriArchivo!!)!!
            var reader = CSVReaderBuilder(InputStreamReader(inputStream)).withSkipLines(1).build()//CSVReader(InputStreamReader(inputStream),CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 1)
            val datos : List<Array<String>> = reader.readAll()

            //clienteViewModel.borrarTodosClientes()

            for(renglon in datos)
            {
                var clienteNuevo = Cliente(renglon[0], renglon[1], renglon[3].toDouble(), renglon[2])
                clienteViewModel.insertarCliente(clienteNuevo)

            }

            Toast.makeText(context, "Tabla de Clientes Actualizada", Toast.LENGTH_LONG).show()

        }
    }

    /*fun descomponerPoblarClientes(uriArchivo : Uri?)
    {

        uriArchivo.toString()
        val myUri : Uri = Uri.parse(uriArchivo.toString())

        CSVReader
        val inputStreamReader = InputStreamReader(getContentResolver().openInputStream(myUri))
        val bufferedReader = BufferedReader(inputStreamReader)
        val sb = StringBuilder()
        var s: String?
        while ((bufferedReader.readLine().also { s = it }) != null) {
            sb.append(s)
        }
        val fileContent = sb.toString()
    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuOpcionesImpExpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuOpcionesImpExpFragment().apply {
                arguments = Bundle().apply {
                    //putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }
}