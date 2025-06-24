import math
import random
import numpy as np
import matplotlib
matplotlib.use('TkAgg')

from matplotlib import pyplot as plt
from matplotlib.patches import Rectangle, Polygon

SHEET_WIDTH = 0
SHEET_HEIGHT = 0
MIN_DISTANCE = 5

parts = []

def rotate_vertex(vx, vy, center_x, center_y, angle):
    cos_theta = np.cos(np.deg2rad(angle))
    sin_theta = np.sin(np.deg2rad(angle))

    dx = vx - center_x
    dy = vy - center_y

    rotated_dx = dx * cos_theta - dy * sin_theta
    rotated_dy = dx * sin_theta + dy * cos_theta

    new_vx = center_x + rotated_dx
    new_vy = center_y + rotated_dy

    return round(new_vx), round(new_vy)


def check_triangle_inside(x, y, a, b, c):
    vertices = [
        (x - a / 2, y - b / 2),
        (x + a / 2, y - b / 2),
        (x, y + b / 2)
    ]

    angles = [0, 30, 60, 90]
    for angle in angles:
        rot_vertices = []

        for vx, vy in vertices:
            new_vx, new_vy = rotate_vertex(vx, vy, x, y, angle)
            rot_vertices.append((new_vx, new_vy))

        for rx, ry in rot_vertices:
            if (
                    rx < MIN_DISTANCE or rx > SHEET_WIDTH - MIN_DISTANCE or
                    ry < MIN_DISTANCE or ry > SHEET_HEIGHT - MIN_DISTANCE
            ):
                return False

    return True


class Individual:
    def __init__(self, genes=None):
        self.genes = genes if genes is not None else []
        self.fitness = 0.0
        self.used_area = 0.0

        if not genes:
            self.initialize_random()

    def initialize_random(self):
        for part in parts:
            shape, params, count = part
            for _ in range(count):
                if shape == 'rectangle':
                    w, h = params
                    x = random.randint(MIN_DISTANCE, SHEET_WIDTH - w - MIN_DISTANCE)
                    y = random.randint(MIN_DISTANCE, SHEET_HEIGHT - h - MIN_DISTANCE)
                    if random.random() < 0.5:
                        w, h = h, w
                    self.genes.append(('rectangle', x, y, w, h))
                elif shape == 'circle':
                    r = params[0]
                    x = random.randint(MIN_DISTANCE + r, SHEET_WIDTH - r - MIN_DISTANCE)
                    y = random.randint(MIN_DISTANCE + r, SHEET_HEIGHT - r - MIN_DISTANCE)
                    self.genes.append(('circle', x, y, r))
                elif shape == 'triangle':
                    a, b, c = params
                    x = random.randint(MIN_DISTANCE, SHEET_WIDTH - max(a, b, c) - MIN_DISTANCE)
                    y = random.randint(MIN_DISTANCE, SHEET_HEIGHT - max(a, b, c) - MIN_DISTANCE)
                    if random.random() < 0.5:
                        a, b, c = b, a, c
                    self.genes.append(('triangle', x, y, a, b, c))

    def is_valid_position(self, index, new_x, new_y, *args):
        def shape_overlap(g1, g2):
            t1_type, x1, y1, *p1 = g1
            if t1_type == 'rectangle':
                x1_min, y1_min, x1_max, y1_max = (x1, y1, x1 + p1[0], y1 + p1[1])
            elif t1_type == 'circle':
                x1_min, y1_min, x1_max, y1_max = (x1 - p1[0], y1 - p1[0], x1 + p1[0], y1 + p1[0])
            elif t1_type == 'triangle':
                x1_min, y1_min, x1_max, y1_max = (
                min([x1 - p1[0] / 2, x1 + p1[0] / 2]), min([y1 - p1[1] / 2, y1 + p1[1] / 2]),
                max([x1 - p1[0] / 2, x1 + p1[0] / 2]), max([y1 - p1[1] / 2, y1 + p1[1] / 2]))

            t2_type, x2, y2, *p2 = g2
            if t2_type == 'rectangle':
                x2_min, y2_min, x2_max, y2_max = (x2, y2, x2 + p2[0], y2 + p2[1])
            elif t2_type == 'circle':
                x2_min, y2_min, x2_max, y2_max = (x2 - p2[0], y2 - p2[0], x2 + p2[0], y2 + p2[0])
            elif t2_type == 'triangle':
                x2_min, y2_min, x2_max, y2_max = (
                min([x2 - p2[0] / 2, x2 + p2[0] / 2]), min([y2 - p2[1] / 2, y2 + p2[1] / 2]),
                max([x2 - p2[0] / 2, x2 + p2[0] / 2]), max([y2 - p2[1] / 2, y2 + p2[1] / 2]))

            if x1_max + MIN_DISTANCE <= x2_min or x2_max + MIN_DISTANCE <= x1_min:
                return False
            if y1_max + MIN_DISTANCE <= y2_min or y2_max + MIN_DISTANCE <= y1_min:
                return False

            return True

        if isinstance(args, tuple):
            new_gen = self.genes[index][:1] + (new_x, new_y) + tuple(list(args))
        else:
            new_gen = self.genes[index][:1] + (new_x, new_y, args)

        if new_gen[0] == 'rectangle':
            w, h = args
            if new_x < MIN_DISTANCE or new_x + w > SHEET_WIDTH - MIN_DISTANCE or \
                    new_y < MIN_DISTANCE or new_y + h > SHEET_HEIGHT - MIN_DISTANCE:
                return False
        elif new_gen[0] == 'circle':
            r = args[0]
            if new_x - r < MIN_DISTANCE or new_x + r > SHEET_WIDTH - MIN_DISTANCE or \
                    new_y - r < MIN_DISTANCE or new_y + r > SHEET_HEIGHT - MIN_DISTANCE:
                return False
        elif new_gen[0] == 'triangle':
            if not check_triangle_inside(new_x, new_y, *args):
                return False

        for i, gen in enumerate(self.genes):
            if i != index:
                if shape_overlap(new_gen, gen):
                    return False
        return True

    def resolve_conflict(self):
        for i in range(len(self.genes)):
            shape, x, y, *params = self.genes[i]

            if not self.is_valid_position(i, x, y, *params):
                attempts = 400

                while attempts > 0:
                    if shape == 'rectangle':
                        w, h = params
                        new_x = random.randint(MIN_DISTANCE, SHEET_WIDTH - w - MIN_DISTANCE)
                        new_y = random.randint(MIN_DISTANCE, SHEET_HEIGHT - h - MIN_DISTANCE)

                        if new_x < MIN_DISTANCE or new_x + w > SHEET_WIDTH - MIN_DISTANCE or \
                                new_y < MIN_DISTANCE or new_y + h > SHEET_HEIGHT - MIN_DISTANCE:
                            return False

                    elif shape == 'circle':
                        r = params[0]
                        new_x = random.randint(MIN_DISTANCE + r, SHEET_WIDTH - r - MIN_DISTANCE)
                        new_y = random.randint(MIN_DISTANCE + r, SHEET_HEIGHT - r - MIN_DISTANCE)

                        if new_x - r < MIN_DISTANCE or new_x + r > SHEET_WIDTH - MIN_DISTANCE or \
                                new_y - r < MIN_DISTANCE or new_y + r > SHEET_HEIGHT - MIN_DISTANCE:
                            return False

                    elif shape == 'triangle':
                        a, b, c = params
                        new_x = random.randint(MIN_DISTANCE, SHEET_WIDTH - max(a, b, c) - MIN_DISTANCE)
                        new_y = random.randint(MIN_DISTANCE, SHEET_HEIGHT - max(a, b, c) - MIN_DISTANCE)

                        if new_x < MIN_DISTANCE or new_x + max(a, b, c) > SHEET_WIDTH - MIN_DISTANCE or \
                                new_y < MIN_DISTANCE or new_y + max(a, b, c) > SHEET_HEIGHT - MIN_DISTANCE:
                            return False

                    if self.is_valid_position(i, new_x, new_y, *params):
                        self.genes[i] = (shape, new_x, new_y, *params)
                        break
                    attempts -= 1

    def calc_fitness(self):
        max_x = max_y = 0
        used_area = 0.0
        penalty = 0.0

        for i, gen in enumerate(self.genes):
            shape, x, y, *params = gen

            if not self.is_valid_position(i, x, y, *params):
                penalty += 1.0

            if shape == 'rectangle':
                w, h = params
                used_area += w * h
                max_x = max(max_x, x + w)
                max_y = max(max_y, y + h)
            elif shape == 'circle':
                r = params[0]
                used_area += math.pi * r ** 2
                max_x = max(max_x, x + r)
                max_y = max(max_y, y + r)
            elif shape == 'triangle':
                a, b, c = params
                s = (a + b + c) / 2
                area = math.sqrt(max(s * (s - a) * (s - b) * (s - c), 0))
                used_area += area
                max_x = max(max_x, x + max(a, b, c) / 2)
                max_y = max(max_y, y + max(a, b, c) / 2)

        self.used_area = used_area
        bounding_box = max(1.0, max_x * max_y)
        self.fitness = max(0.0, used_area / bounding_box - penalty * 0.1)
        return self.fitness

    def crossover(self, partner):
        child = Individual()
        child.genes = []

        if len(self.genes) >= len(partner.genes):
            cross_point = random.randint(1, len(partner.genes))
        else:
            cross_point = random.randint(1, len(self.genes))

        for i in range(len(self.genes)):
            if i < cross_point:
                child.genes.append(self.genes[i])
            else:
                if i < len(partner.genes):
                    child.genes.append(partner.genes[i])
                else:
                    child.genes.append(None)

        child.genes = [gene for gene in child.genes if gene]
        child.resolve_conflict()
        return child

    def mutate(self, mutation_rate=0.020):
        for i in range(len(self.genes)):
            if random.random() < mutation_rate:
                shape, x, y, *params = self.genes[i]

                if shape == 'rectangle':
                    w, h = params
                    new_x = random.randint(MIN_DISTANCE, SHEET_WIDTH - w - MIN_DISTANCE)
                    new_y = random.randint(MIN_DISTANCE, SHEET_HEIGHT - h - MIN_DISTANCE)

                    if random.random() < 0.5:
                        w, h = h, w

                elif shape == 'circle':
                    r = params[0]
                    new_x = random.randint(MIN_DISTANCE + r, SHEET_WIDTH - r - MIN_DISTANCE)
                    new_y = random.randint(MIN_DISTANCE + r, SHEET_HEIGHT - r - MIN_DISTANCE)

                elif shape == 'triangle':
                    a, b, c = params
                    new_x = random.randint(MIN_DISTANCE, SHEET_WIDTH - max(a, b, c) - MIN_DISTANCE)
                    new_y = random.randint(MIN_DISTANCE, SHEET_HEIGHT - max(a, b, c) - MIN_DISTANCE)

                    if random.random() < 0.5:
                        a, b, c = b, a, c

                if self.is_valid_position(i, new_x, new_y, *params):
                    self.genes[i] = (shape, new_x, new_y, *params)

    @staticmethod
    def compact_solution(individual):
        sorted_gens = []
        for g in individual.genes:
            try:
                if g[0] == 'rectangle':
                    key_value = g[3] * g[4]
                elif g[0] == 'circle':
                    key_value = math.pi * g[3] ** 2
                elif g[0] == 'triangle':
                    key_value = sum(g[3:])

                sorted_gens.append((key_value, g))
            except IndexError:
                print("Ошибка обработки гена:", g)
                pass

        sorted_gens.sort(reverse=True)
        new_genes = [g for _, g in sorted_gens]
        return Individual(new_genes)


class GeneticAlgorithm:
    def __init__(self, population_size=100, generations=100):
        self.population_size = population_size
        self.generations = generations
        self.population = [Individual() for _ in range(population_size)]

    def selection(self):
        tournament = random.sample(self.population, 3)
        return max(tournament, key=lambda x: x.fitness)

    def evolve(self, progress_callback=None):
        best_ind = None
        best_fit_history = []

        for generation in range(self.generations):
            for individual in self.population:
                individual.calc_fitness()

            self.population.sort(key=lambda x: x.fitness, reverse=True)
            best = self.population[0]
            best_fit_history.append(best.fitness)

            if progress_callback:
                percent = int((generation + 1) / self.generations * 100)
                progress_callback(percent)
            print(f"Поколение {generation}: Приспособленность = {best.fitness:.4f}")

            new_population = [best]

            while len(new_population) < self.population_size:
                parent1 = self.selection()
                parent2 = self.selection()
                child = parent1.crossover(parent2)
                child.mutate()
                child = Individual.compact_solution(child)
                new_population.append(child)

            self.population = new_population

        self.population.sort(key=lambda x: x.fitness, reverse=True)
        return self.population[0], best_fit_history


def visualize_solution(solution):
    fig, ax = plt.subplots(figsize=(12, 6))
    ax.add_patch(Rectangle((0, 0), SHEET_WIDTH, SHEET_HEIGHT, fill=False, edgecolor='blue', linewidth=2))
    ax.add_patch(
        Rectangle((MIN_DISTANCE, MIN_DISTANCE), SHEET_WIDTH - 2 * MIN_DISTANCE, SHEET_HEIGHT - 2 * MIN_DISTANCE,
                  fill=False, edgecolor='red', linestyle='--', linewidth=1))
    colors = plt.cm.get_cmap('tab10', len(solution.genes))

    max_x = 0
    max_y = 0

    for i, gen in enumerate(solution.genes):
        shape, x, y, *params = gen

        if shape == 'rectangle':
            w, h = params
            ax.add_patch(Rectangle((x, y), w, h, fill=True, ec='grey', fc=colors(i), alpha=0.7))
            ax.text(x + w / 2, y + h / 2, f"{w}x{h}", ha='center', va='center', fontsize=8)
            max_x = max(max_x, x + w)
            max_y = max(max_y, y + h)
        elif shape == 'circle':
            r = params[0]
            circle = plt.Circle((x, y), radius=r, color=colors(i), alpha=0.7)
            ax.add_artist(circle)
            ax.text(x, y + r + 10, f"R={r}", ha='center', va='center', fontsize=8)
            max_x = max(max_x, x + r)
            max_y = max(max_y, y + r)
        elif shape == 'triangle':
            a, b, c = params
            vertices = [(x - a / 2, y - b / 2), (x + a / 2, y - b / 2), (x, y + b / 2)]
            triangle = Polygon(vertices, closed=True, color=colors(i), alpha=0.7)
            ax.add_patch(triangle)
            ax.text(x, y, f"A={a}, B={b}, C={c}", ha='center', va='center', fontsize=8)
            max_x = max(max_x, x + max(a, b, c) / 2)
            max_y = max(max_y, y + max(a, b, c) / 2)

    padding = 3
    rect_x = MIN_DISTANCE
    rect_y = MIN_DISTANCE
    width = min(max_x + padding, SHEET_WIDTH - MIN_DISTANCE) - rect_x
    height = min(max_y + padding, SHEET_HEIGHT - MIN_DISTANCE) - rect_y

    ax.add_patch(Rectangle((rect_x, rect_y), width, height, fill=False, edgecolor='green', linestyle='--', linewidth=2))

    ax.set_xlim(0, SHEET_WIDTH)
    ax.set_ylim(0, SHEET_HEIGHT)
    ax.set_aspect('equal')

    work_area = (SHEET_WIDTH - 2 * MIN_DISTANCE) * (SHEET_HEIGHT - 2 * MIN_DISTANCE)
    area_cm = solution.used_area / work_area
    occupancy = 100 * solution.used_area / work_area

    plt.title(f"Оптимальный раскрой (Fitness: {solution.fitness:.4f}), "
              f"Площадь: {area_cm:.2f} см², Заполнение: {occupancy:.1f}%")
    plt.show()