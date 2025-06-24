import threading
import queue
import tkinter as tk
from tkinter import ttk, messagebox

import ga_main
from ga_main import GeneticAlgorithm, visualize_solution, parts

data_q = queue.Queue()
best_solution = None
user_parts = []


def add_part():
    shape = shape_v.get()
    try:
        count = int(count_ent.get())

        if shape == 'rectangle':
            width = int(param1_entry.get())
            height = int(param2_entry.get())
            user_parts.append(('rectangle', (width, height), count))
        elif shape == 'circle':
            rad = int(param1_entry.get())
            user_parts.append(('circle', (rad,), count))
        elif shape == 'triangle':
            a = int(param1_entry.get())
            b = int(param2_entry.get())
            c = int(param3_entry.get())
            user_parts.append(('triangle', (a, b, c), count))
        update_parts_list()
    except ValueError:
        messagebox.showerror("Ошибка", "Введите конкретные числовые значения")


def update_parts_list():
    parts_listbox.delete(0, tk.END)
    for i, part in enumerate(user_parts):
        parts_listbox.insert(i, f"{part[0]} x{part[2]} {part[1]}")


def clear_parts():
    user_parts.clear()
    update_parts_list()


def check_q():
    while True:
        try:
            item = data_q.get(block=False)
            if item is None:
                break
            elif item['type'] == 'progress':
                progress['value'] = item['value']
            elif item['type'] == 'solution':
                show_res(item['value'])
        except queue.Empty:
            break
    root.after(5, check_q)


def show_res(solution):
    global best_solution
    best_solution = solution
    visualize_solution(best_solution)
    progress["value"] = 0


def run_algorithm():
    if not user_parts:
        messagebox.showerror("Ошибка", "Добавьте хотя бы одну фигуру")
        return

    parts.clear()
    parts.extend(user_parts)

    SHEET_W = int(param4_entry.get())
    SHEET_H = int(param5_entry.get())

    ga_main.SHEET_WIDTH = SHEET_W
    ga_main.SHEET_HEIGHT = SHEET_H

    top_level = tk.Toplevel(root)
    top_level.title('Прогресс')
    top_level.geometry('300x100')

    progress_bar = ttk.Progressbar(top_level, orient="horizontal", mode="determinate", length=200, maximum=100)
    progress_bar.pack(pady=20)

    def run_thread():
        try:
            def upd_progress(percent):
                root.after(0, lambda: progress_bar['value'] == percent)

            ga = GeneticAlgorithm(population_size=100, generations=100)
            best_solution, _ = ga.evolve(progress_callback=upd_progress)

            root.after(0, lambda: visualize_solution(best_solution))

        except Exception as e:
            messagebox.showerror("Ошибка", str(e))
        finally:
            top_level.destroy()

    threading.Thread(target=run_thread).start()


def upd_fields(*args):
    shape = shape_v.get()
    if shape == 'rectangle':
        param1_label.config(text="Ширина")
        param2_label.config(text="Высота")
        param3_label.grid_remove()
        param1_entry.grid()
        param2_entry.grid()
        param3_entry.grid_remove()
    if shape == 'circle':
        param1_label.config(text="Радиус")
        param2_label.grid_remove()
        param3_label.grid_remove()
        param1_entry.grid()
        param2_entry.grid_remove()
        param3_entry.grid_remove()
    if shape == 'triangle':
        param1_label.config(text="Сторона A")
        param2_label.config(text="Сторона B")
        param3_label.config(text="Сторона C")
        param1_entry.grid()
        param2_entry.grid()
        param3_entry.grid()


root = tk.Tk()
root.title("Конфигуратор фигур")

frame = ttk.Frame(root, padding="10")
frame.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))

shape_v = tk.StringVar(value='rectangle')
ttk.Label(frame, text="Фигура:").grid(column=0, row=0, sticky=tk.W)
shape_menu = ttk.OptionMenu(frame, shape_v, 'rectangle', 'rectangle', 'circle', 'triangle', command=upd_fields)
shape_menu.grid(column=1, row=0, sticky=tk.W)

ttk.Label(frame, text="Количество:").grid(column=0, row=1, sticky=tk.W)
count_ent = ttk.Entry(frame, width=10)
count_ent.grid(column=1, row=1)

param1_label = ttk.Label(frame, text="Ширина")
param1_label.grid(column=0, row=2, sticky=tk.W)
param1_entry = ttk.Entry(frame, width=10)
param1_entry.grid(column=1, row=2)

param2_label = ttk.Label(frame, text="Высота")
param2_label.grid(column=0, row=3, sticky=tk.W)
param2_entry = ttk.Entry(frame, width=10)
param2_entry.grid(column=1, row=3)

param3_label = ttk.Label(frame, text="Сторона C")
param3_label.grid(column=0, row=4, sticky=tk.W)
param3_entry = ttk.Entry(frame, width=10)
param3_entry.grid(column=1, row=4)
param3_entry.grid_remove()

param4_label = ttk.Label(frame, text="Ширина листа, мм")
param4_label.grid(column=0, row=5, sticky=tk.W)
param4_entry = ttk.Entry(frame, width=10)
param4_entry.grid(column=1, row=5)

param5_label = ttk.Label(frame, text="Длина листа, мм")
param5_label.grid(column=0, row=6, sticky=tk.W)
param5_entry = ttk.Entry(frame, width=10)
param5_entry.grid(column=1, row=6)

ttk.Button(frame, text="Добавить фигуру", command=add_part).grid(column=0, row=7, columnspan=2, pady=5)
ttk.Button(frame, text="Очистить", command=clear_parts).grid(column=0, row=8, columnspan=2, pady=5)
ttk.Button(frame, text="Запустить алгоритм", command=run_algorithm).grid(column=0, row=9, columnspan=2, pady=10)

ttk.Label(frame, text="Добавленные фигуры:").grid(column=0, row=10, columnspan=2)
parts_listbox = tk.Listbox(frame, height=5, width=40)
parts_listbox.grid(column=0, row=11, columnspan=2)

progress = ttk.Progressbar(frame, orient="horizontal", mode="determinate", length=200, maximum=100)

upd_fields()

root.mainloop()