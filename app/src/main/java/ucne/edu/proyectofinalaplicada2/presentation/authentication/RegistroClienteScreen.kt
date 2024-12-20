package ucne.edu.proyectofinalaplicada2.presentation.authentication


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ucne.edu.proyectofinalaplicada2.R
import ucne.edu.proyectofinalaplicada2.presentation.components.CedulaVisualTransformation
import ucne.edu.proyectofinalaplicada2.presentation.components.PhoneNumberVisualTransformation
import ucne.edu.proyectofinalaplicada2.ui.theme.Gradiend


@Composable
fun RegistroClienteScreen(
    goToBack: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),

    ) {
    val uiState by viewModel.uistate.collectAsStateWithLifecycle()
    RegistroClienteBodyScreen(
        uiState = uiState,
        goToBack = goToBack,
        onEvent = { event -> viewModel.onEvent(event) },
    )
}

@Composable
fun RegistroClienteBodyScreen(
    uiState: ClienteUiState,
    goToBack: () -> Unit,
    onEvent: (AuthEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF645455), Color(0xFF4A28BA)), // Gradiente
                    start = Offset(0f, 0f),
                    end = Offset.Infinite
                )
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo1),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Registro de Usuario",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White, // Texto en blanco para contraste
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.nombre,
                    onValueChange = { onEvent(AuthEvent.OnchangeNombre(it)) },
                    label = { Text("Nombre", color = Color.White) },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)

                )
                OutlinedTextField(
                    value = uiState.apellidos,
                    onValueChange = { onEvent(AuthEvent.OnchangeApellidos(it)) },
                    label = { Text("Apellidos", color = Color.White) },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)

                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ){
                ErrorText(uiState.errorNombre)
                ErrorText(uiState.errorApellidos)
            }


            Spacer(modifier = Modifier.height(8.dp))


            PhoneInputField(
                phone = uiState.celular,
                onPhoneChange = { onEvent(AuthEvent.OnchangeCelular(it)) },
            )
            ErrorText(uiState.errorCelular)

            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(AuthEvent.OnChangeEmail(it)) },
                label = { Text("Email", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)

            )
            ErrorText(uiState.errorEmail)

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CedulaInputField(
                    cedula = uiState.cedula,
                    onCedulaChange = { onEvent(AuthEvent.OnchangeCedula(it)) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.direccion,
                    onValueChange = { onEvent(AuthEvent.OnchangeDireccion(it)) },
                    label = { Text("Direccion", color = Color.White) },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)

                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)

            ){
                ErrorText(uiState.errorCedula)
                ErrorText(uiState.errorDireccion)
            }


            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = uiState.password,
                onValueChange = { onEvent(AuthEvent.OnChangePassword(it)) },
                label = { Text("Contraseña", color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)

            )
            ErrorText(uiState.errorPassword)

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedButton(
                onClick = { onEvent(AuthEvent.Signup) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Registrar")
                Text(text = "Registrar", modifier = Modifier.padding(start = 8.dp))

            }
        }

        FloatingActionButton(
            onClick = goToBack,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = 16.dp),
            containerColor = Color.White,
            contentColor = Color(0xFF4A28BA)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver"
            )
        }
    }
}

@Composable
fun ErrorText(error: String?) {
    error?.let {
        Text(
            text = it,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}



@Composable
fun PhoneInputField(
    phone: String,
    onPhoneChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = phone,
        onValueChange = {
            val onlyDigits = it.filter { char -> char.isDigit() }
            if (onlyDigits.length <= 10) {
                onPhoneChange(onlyDigits)
            }
        },
        visualTransformation = PhoneNumberVisualTransformation(),
        label = { Text("Número de Teléfono", color = Gradiend) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
    )
}

@Composable
fun PhoneInputField2(
    phone: String,
    onPhoneChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = phone,
        onValueChange = {
            val onlyDigits = it.filter { char -> char.isDigit() }
            if (onlyDigits.length <= 10) {
                onPhoneChange(onlyDigits)
            }
        },
        visualTransformation = PhoneNumberVisualTransformation(),
        label = { Text("Número de Teléfono") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
    )
}

@Composable
fun CedulaInputField(
    cedula: String,
    onCedulaChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = cedula,
        onValueChange = {
            val onlyDigits = it.filter { char -> char.isDigit() }
            if (onlyDigits.length <= 11) {
                onCedulaChange(onlyDigits)
            }
        },
        visualTransformation = CedulaVisualTransformation(),
        label = { Text("Cédula", color = Gradiend) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
    )
}