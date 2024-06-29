package com.example.proyectointegradorappsmoviles.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.proyectointegradorappsmoviles.R
import com.example.proyectointegradorappsmoviles.entities.Venta
import com.example.proyectointegradorappsmoviles.viewmodels.ClienteViewmodel
import com.example.proyectointegradorappsmoviles.viewmodels.VentaViewModel
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar


/**
 * A simple [Fragment] subclass.
 * Use the [RegistrarVentaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrarVentaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    /*private var param1: String? = null
    private var param2: String? = null*/

    private lateinit var ventaViewmodel : VentaViewModel
    private lateinit var clienteViewmodel : ClienteViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            /*param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)*/
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registrar_venta, container, false)

        //Viewmodels generados
        ventaViewmodel = ViewModelProvider(this).get(VentaViewModel::class.java)
        clienteViewmodel = ViewModelProvider(this).get(ClienteViewmodel::class.java)

        //Obteniendo elementos de vista
        val spinnerClientes: Spinner = view.findViewById(R.id.spinnerListadoClientes)
        val textNombreCompletoClienteSeleccionado : TextView = view.findViewById(R.id.textNombreCienteCOmpletoSeleccionado)
        val editTextPrecioUnitario : EditText = view.findViewById(R.id.editTextPrecioUnitario)
        val editTextCantidadVenta : EditText = view.findViewById(R.id.editTextCantidadVenta)
        val editTextVentaTotal : EditText = view.findViewById(R.id.editTextVentaTotal)
        val switchVentaPagada : Switch = view.findViewById(R.id.switchVentaPagada)
        val botonIngresarVenta : Button = view.findViewById(R.id.botonGuardarVenta)

        //Poblado de Spinner en base de valores
        var clientes = ArrayList<String>()
        var adaptadorVentas = ArrayAdapter<String>(
            view.getContext(),
            android.R.layout.simple_dropdown_item_1line,
            clientes
        )

        spinnerClientes.setAdapter(adaptadorVentas)
        adaptadorVentas.notifyDataSetChanged();
        adaptadorVentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        clienteViewmodel.repositorio.listadoNombresCliente.observe(viewLifecycleOwner)
        {
            lista ->

            var adaptadorVentas = ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_dropdown_item_1line,
                lista
            )
            spinnerClientes.setAdapter(adaptadorVentas)
            adaptadorVentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        //Metodo para selección de Cliente en Spinner
        spinnerClientes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var listaClientes = almecenarEnlistadoVariableClientes(spinnerClientes)
                var itemActual = listaClientes[position]

                clienteViewmodel.repositorio.listadoCompletoClientes.observe(viewLifecycleOwner)
                {
                    lista ->

                        var listaClienteFiltrada = lista.filter{ p -> lista.any{itemActual == p.nombreCorto} }
                        var clienteSeleccionado = listaClienteFiltrada.get(0)

                        textNombreCompletoClienteSeleccionado.text = clienteSeleccionado.nombreCompleto
                        editTextPrecioUnitario.setText(clienteSeleccionado.precioVenta.toString())

                }
            }

        }

        //Evento para edición de Cantidad de ventas
        editTextCantidadVenta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable)
            {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int)
            {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
            {
                if (editTextCantidadVenta.text.toString() != "")
                {
                    var precioUnitario = editTextPrecioUnitario.text.toString().toDouble()
                    var cantidadIngresada = editTextCantidadVenta.text.toString().toInt()

                    var ventaTotal = precioUnitario * cantidadIngresada

                    editTextVentaTotal.setText(ventaTotal.toString())
                }

                else
                {
                    editTextVentaTotal.setText("0.00")
                }
            }
        })

        //Manejo para ingresar venta en BD
        botonIngresarVenta.setOnClickListener()
        {
            if(editTextCantidadVenta.text.toString() != "")
            {
                val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date: java.util.Date = Calendar.getInstance().time;
                val dateString : String = dateFormat.format(date)

                val nuevaVenta = Venta(0, spinnerClientes.selectedItem.toString(), editTextCantidadVenta.text.toString().toInt(), dateString, switchVentaPagada.isChecked, editTextVentaTotal.text.toString().toDouble())
                ventaViewmodel.insertarVenta(nuevaVenta)

                findNavController().navigate(R.id.action_registrarVentaFragment_to_menuPrincipalFragment)

                Toast.makeText(context, "Venta Nueva Generada", Toast.LENGTH_LONG).show()
            }

            else
            {
                Toast.makeText(context, "Falta Ingresar la Cantidad de la Venta", Toast.LENGTH_LONG).show()
            }

        }

        return view
    }

    fun almecenarEnlistadoVariableClientes  (spinnerClientes : Spinner) : ArrayList<String>
    {
        val adapter = spinnerClientes.adapter
        val n: Int = adapter.getCount()
        val clientes = ArrayList<String>(n)
        for (i in 0 until n) {
            val cliente = adapter.getItem(i) as String
            clientes.add(cliente)
        }
        return clientes
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistrarVentaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrarVentaFragment().apply {
                arguments = Bundle().apply {
                    /*putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)*/
                }
            }
    }
}