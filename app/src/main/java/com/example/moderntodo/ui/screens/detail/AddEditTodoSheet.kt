package com.example.moderntodo.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moderntodo.domain.model.Category
import com.example.moderntodo.domain.model.Priority
import com.example.moderntodo.domain.model.TodoItem
import com.example.moderntodo.ui.components.getCategoryIcon
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditTodoSheet(
    todoToEdit: TodoItem?,
    onDismiss: () -> Unit,
    onSave: (TodoItem) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    var title by remember(todoToEdit) { mutableStateOf(todoToEdit?.title ?: "") }
    var description by remember(todoToEdit) { mutableStateOf(todoToEdit?.description ?: "") }
    var selectedCategory by remember(todoToEdit) {
        mutableStateOf(
            if (todoToEdit?.category == null || todoToEdit.category == Category.ALL) Category.PERSONAL
            else todoToEdit.category
        )
    }
    var selectedPriority by remember(todoToEdit) {
        mutableStateOf(todoToEdit?.priority ?: Priority.MEDIUM)
    }
    var dueDate by remember(todoToEdit) { mutableStateOf(todoToEdit?.dueDate) }
    var showDatePicker by remember { mutableStateOf(false) }

    val isEditing = todoToEdit != null
    val scrollState = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(scrollState)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEditing) "Edit Tugas" else "Tugas Baru",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title Input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul Tugas") },
                placeholder = { Text("Misal: Selesaikan laporan mingguan") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.small
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description Input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Deskripsi / Catatan (Opsional)") },
                placeholder = { Text("Detail atau langkah pengerjaan...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                shape = MaterialTheme.shapes.small
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Priority Selection
            Text(
                text = "Prioritas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Priority.entries.forEach { priority ->
                    val isSelected = priority == selectedPriority
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedPriority = priority },
                        label = { Text(priority.displayName) },
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.small
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Category Selection
            Text(
                text = "Kategori",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Category.defaultCategories().forEach { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = { Text(category.displayName) },
                        leadingIcon = {
                            Icon(
                                imageVector = getCategoryIcon(category),
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        },
                        shape = MaterialTheme.shapes.small
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Due Date Row
            Text(
                text = "Tenggat Waktu",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { showDatePicker = true },
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (dueDate != null) {
                            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(dueDate!!))
                        } else {
                            "Pilih Tanggal"
                        }
                    )
                }
                if (dueDate != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { dueDate = null }) {
                        Text("Hapus Tanggal")
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Save Button
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val newItem = (todoToEdit ?: TodoItem(title = "")).copy(
                            title = title.trim(),
                            description = description.trim(),
                            category = selectedCategory,
                            priority = selectedPriority,
                            dueDate = dueDate
                        )
                        onSave(newItem)
                        onDismiss()
                    }
                },
                enabled = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEditing) "Simpan Perubahan" else "Tambah Tugas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = dueDate ?: System.currentTimeMillis()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    dueDate = datePickerState.selectedDateMillis
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Batal")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
